package orre.threads;

import orre.resources.FileToLoad;
import orre.resources.ProgressTracker;
import orre.resources.ResourceFile;
import orre.resources.ResourceQueue;
import orre.resources.loaders.ModelLoader;
import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableModel;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;

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
			if((currentFile.fileType == ResourceFile.MENU_TEXTURE_FILE) || (currentFile.fileType == ResourceFile.TEXTURE_FILE))
			{
				PartiallyLoadableTexture texture = TextureLoader.partiallyLoadTextureFromFile(currentFile);
				texture.setDestinationCache(currentFile.destinationCache);
				this.resourceQueue.enqueueResourceForFinalization(texture);
			} else if(currentFile.fileType == ResourceFile.MODEL_FILE)
			{
				PartiallyLoadableModel model = ModelLoader.loadModel(currentFile);
				model.setDestinationCache(currentFile.destinationCache);
				this.resourceQueue.enqueueResourceForFinalization(model);
			}
			currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
			this.tracker.registerFileLoaded();
		}
	}
}
