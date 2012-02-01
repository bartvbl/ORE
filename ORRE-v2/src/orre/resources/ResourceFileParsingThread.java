package orre.resources;

import orre.util.Queue;

public class ResourceFileParsingThread extends Thread{

	private Queue<String> remainingItemsQueue;
	private Queue<ResourceFile> itemsTypeQueue;
	private ResourceQueue resourceQueue;

	public ResourceFileParsingThread(ResourceQueue queue, Queue<String> itemsToLoadQueue, Queue<ResourceFile> itemsTypeQueue) {
		this.remainingItemsQueue = itemsToLoadQueue;
		this.itemsTypeQueue = itemsTypeQueue;
		this.resourceQueue = queue;
	}
	
	public void run()
	{
		String src = this.remainingItemsQueue.dequeue();
		while(src != null)
		{
			this.parseResourceFile(src);
			src = this.remainingItemsQueue.dequeue();
		}
		
	}

	private void parseResourceFile(String src) {
		final ResourceFile fileTypeToParse = this.itemsTypeQueue.dequeue();
		if(fileTypeToParse == ResourceFile.RESOURCE_LIST_FILE)
		{
			System.out.println("loading file " + src);
			ResourceListFileParser.parseFile(src, this.resourceQueue);
		}
	}
}
