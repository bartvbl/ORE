package orre.resources;

import orre.resources.loaders.TextureLoader;

public class ResourceLoadingThread extends Thread {
	private ResourceQueue resourceQueue;

	public ResourceLoadingThread(ResourceQueue queue)
	{
		this.resourceQueue = queue;
	}
	
	public void run()
	{
		FileToLoad currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		while(currentFile != null)
		{
			if(currentFile.fileType == ResourceFile.TEXTURE_FILE)
			{
				TextureLoader.loadTextureFromFile(currentFile);
			}
			currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		}
	}
}
