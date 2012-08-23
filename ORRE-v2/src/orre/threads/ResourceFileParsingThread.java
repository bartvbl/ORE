package orre.threads;

import orre.resources.FileToLoad;
import orre.resources.ResourceFile;
import orre.resources.ResourceListFileParser;
import orre.resources.ResourceQueue;
import orre.util.Queue;

public class ResourceFileParsingThread extends Thread{

	private Queue<FileToLoad> remainingItemsQueue;
	private ResourceQueue resourceQueue;

	public ResourceFileParsingThread(ResourceQueue queue, Queue<FileToLoad> itemsToLoadQueue) {
		this.remainingItemsQueue = itemsToLoadQueue;
		this.resourceQueue = queue;
	}

	public void run()
	{
		FileToLoad file = this.remainingItemsQueue.dequeue();
		while(file != null)
		{
			this.parseResourceFile(file);
			file = this.remainingItemsQueue.dequeue();
		}
		this.resourceQueue.startLoading();
	}

	private void parseResourceFile(FileToLoad file) {
		if(file.fileType == ResourceFile.RESOURCE_LIST_FILE)
		{
			ResourceListFileParser.parseFile(file, this.resourceQueue);
		}
	}
}
