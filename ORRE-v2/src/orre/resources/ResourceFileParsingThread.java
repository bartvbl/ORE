package orre.resources;

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
		System.out.println(this.remainingItemsQueue);
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
