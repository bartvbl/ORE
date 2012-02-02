package orre.resources;

import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;

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
			if((currentFile.fileType == ResourceFile.MENU_TEXTURE_FILE) || (currentFile.fileType == ResourceFile.TEXTURE_FILE))
			{
				PartiallyLoadableTexture texture = TextureLoader.partiallyLoadTextureFromFile(currentFile);
				this.resourceQueue.queueResourceForFinalization(texture);
				System.out.println("loaded texture");
			}
			currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		}
	}
}
