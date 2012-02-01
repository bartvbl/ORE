package orre.resources;

import orre.util.Queue;

public class ResourceFileParsingThread extends Thread{

	private Queue<String> remainingItemsQueue;

	public ResourceFileParsingThread(Queue<String> itemsToLoadQueue, Queue<ResourceFile> itemsTypeQueue) {
		this.remainingItemsQueue = itemsToLoadQueue;
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
		//parse a resource file, add it to a list of file batches in ResourceQueue
	}
}
