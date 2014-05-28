package orre.threads;

import java.io.FileNotFoundException;
import java.io.IOException;

import openrr.map.loader.MapLoader;
import openrr.map.loader.PartiallyLoadableMap;
import orre.animation.Animation;
import orre.animation.AnimationLoader;
import orre.resources.ResourceCache;
import orre.resources.UnloadedResource;
import orre.resources.Finalizable;
import orre.resources.ProgressTracker;
import orre.resources.ResourceType;
import orre.resources.ResourceQueue;
import orre.resources.data.BlueprintModel;
import orre.resources.loaders.LXFMLLoader;
import orre.resources.loaders.ModelLoader;
import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;
import orre.util.FatalExceptionHandler;

public class ResourceLoadingThread extends Thread {
	private final ResourceQueue resourceQueue;
	private final ProgressTracker tracker;
	private final ResourceCache cache;

	public ResourceLoadingThread(ResourceQueue queue, ProgressTracker tracker, ResourceCache cache)
	{
		this.resourceQueue = queue;
		this.tracker = tracker;
		this.cache = cache;
		this.setName("Resource loading thread " + this.getId());
	}
	
	public void run()
	{
		UnloadedResource currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
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

	private void loadFile(UnloadedResource currentFile) throws Exception {
		Finalizable resource = null;
		if((currentFile.fileType == ResourceType.MENU_TEXTURE_FILE) || (currentFile.fileType == ResourceType.TEXTURE_FILE))
		{
			resource = TextureLoader.partiallyLoadTextureFromFile(currentFile);
		} else if(currentFile.fileType == ResourceType.OBJ_MODEL_FILE) {
			resource = ModelLoader.loadModel(currentFile, this.resourceQueue);
		} else if(currentFile.fileType == ResourceType.LXFML_FILE) {
			resource = LXFMLLoader.load(currentFile, this.resourceQueue);
		} else if(currentFile.fileType == ResourceType.MAP_FILE) {
			resource = MapLoader.loadMap(currentFile);
		} else if(currentFile.fileType == ResourceType.ANIMATION_FILE) 
		{
			Animation animation = AnimationLoader.load(currentFile);
			if(animation != null) {
				cache.addAnimation(animation);
			}
		}
		if(resource != null) {
			this.resourceQueue.enqueueResourceForFinalization(resource);
		}
	}
}
