package orre.threads;

import java.io.FileNotFoundException;
import java.io.IOException;

import orre.animation.Animation;
import orre.animation.AnimationLoader;
import orre.resources.FileToLoad;
import orre.resources.Finalizable;
import orre.resources.ProgressTracker;
import orre.resources.ResourceFile;
import orre.resources.ResourceQueue;
import orre.resources.data.BlueprintModel;
import orre.resources.loaders.LXFMLLoader;
import orre.resources.loaders.MapLoader;
import orre.resources.loaders.ModelLoader;
import orre.resources.loaders.TextureLoader;
import orre.resources.loaders.map.PartiallyLoadableMap;
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

	private void loadFile(FileToLoad currentFile) throws Exception {
		Finalizable resource = null;
		if((currentFile.fileType == ResourceFile.MENU_TEXTURE_FILE) || (currentFile.fileType == ResourceFile.TEXTURE_FILE))
		{
			resource = TextureLoader.partiallyLoadTextureFromFile(currentFile);
		} else if(currentFile.fileType == ResourceFile.OBJ_MODEL_FILE) {
			resource = ModelLoader.loadModel(currentFile, this.resourceQueue);
		} else if(currentFile.fileType == ResourceFile.LXFML_FILE) {
			resource = LXFMLLoader.load(currentFile, this.resourceQueue);
		} else if(currentFile.fileType == ResourceFile.MAP_FILE) {
			resource = MapLoader.loadMap(currentFile);
		} else if(currentFile.fileType == ResourceFile.ANIMATION_FILE) 
		{
			Animation animation = AnimationLoader.load(currentFile.getPath());
			if(animation != null) {
				currentFile.destinationCache.addAnimation(animation);
			}
		}
		if(resource != null) {
			resource.setDestinationCache(currentFile.destinationCache);
			this.resourceQueue.enqueueResourceForFinalization(resource);
		}
	}
}
