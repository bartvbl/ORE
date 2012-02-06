package orre.resources;

import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;

public class ResourceLoadingThread extends Thread {
	private ResourceQueue resourceQueue;
	private ProgressTracker tracker;

	public ResourceLoadingThread(ResourceQueue queue, ProgressTracker tracker)
	{
		this.resourceQueue = queue;
		this.tracker = tracker;
	}
	
	public void run()
	{
		FileToLoad currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		while(currentFile != null)
		{
			if((currentFile.fileType == ResourceFile.MENU_TEXTURE_FILE) || (currentFile.fileType == ResourceFile.TEXTURE_FILE))
			{
				PartiallyLoadableTexture texture = TextureLoader.partiallyLoadTextureFromFile(currentFile);
				this.resourceQueue.enqueueResourceForFinalization(texture);
			} else if(currentFile.fileType == ResourceFile.MODEL_FILE)
			{
				System.out.println("model file: " + currentFile.nodeFile.valueOf("@src"));
			}
			currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
			this.tracker.registerFileLoaded();
		}
	}
}
