package orre.threads;

import orre.animation.Animation;
import orre.animation.AnimationLoader;
import orre.resources.FileToLoad;
import orre.resources.ProgressTracker;
import orre.resources.ResourceFile;
import orre.resources.ResourceQueue;
import orre.resources.data.BlueprintModel;
import orre.resources.loaders.TextureLoader;
import orre.resources.loaders.map.MapLoader;
import orre.resources.loaders.map.PartiallyLoadableMap;
import orre.resources.loaders.obj.ModelLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;
import orre.util.FatalExceptionHandler;

public class ResourceLoadingThread extends Thread {
	private ResourceQueue resourceQueue;
	private ProgressTracker tracker;

	public ResourceLoadingThread(ResourceQueue queue, ProgressTracker tracker)
	{
		this.resourceQueue = queue;
		this.tracker = tracker;
		this.setName("Resource loading thread " + this.getId());
	}
	
	public void run()
	{
		FileToLoad currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		while(currentFile != null)
		{
			try {
				loadFile(currentFile);
			} catch(Exception e) {
				e.printStackTrace();
				FatalExceptionHandler.exitWithErrorMessage("Error occurred during file loading. Message:\n" + e.getMessage());
			}
			currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
			this.tracker.registerFileLoaded();
		}
	}

	private void loadFile(FileToLoad currentFile) {
		if((currentFile.fileType == ResourceFile.MENU_TEXTURE_FILE) || (currentFile.fileType == ResourceFile.TEXTURE_FILE))
		{
			PartiallyLoadableTexture texture = TextureLoader.partiallyLoadTextureFromFile(currentFile);
			texture.setDestinationCache(currentFile.destinationCache);
			this.resourceQueue.enqueueResourceForFinalization(texture);
		} else if(currentFile.fileType == ResourceFile.MODEL_FILE)
		{
			BlueprintModel model = ModelLoader.loadModel(currentFile, this.resourceQueue);
			model.setDestinationCache(currentFile.destinationCache);
			this.resourceQueue.enqueueResourceForFinalization(model);
		} else if(currentFile.fileType == ResourceFile.MAP_FILE) {
			PartiallyLoadableMap map = MapLoader.loadMap(currentFile);
			map.setDestinationCache(currentFile.destinationCache);
			this.resourceQueue.enqueueResourceForFinalization(map);
		} else if(currentFile.fileType == ResourceFile.ANIMATION_FILE) {
			Animation animation = AnimationLoader.load(currentFile.getPath());
			if(animation != null) {
				currentFile.destinationCache.addAnimation(animation);
			}
		}
	}
}
