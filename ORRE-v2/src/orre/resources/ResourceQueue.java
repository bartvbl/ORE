package orre.resources;

import orre.util.Queue;

public class ResourceQueue {
	private Queue<String> itemsToLoadQueue;
	
	public ResourceQueue()
	{
		this.itemsToLoadQueue = new Queue<String>();
	}
	
	public void enqueueResourceFile(String src) {
		this.itemsToLoadQueue.enqueue(src);
	}

	public void parseResourceFiles() {
		new ResourceFileParsingThread(this.itemsToLoadQueue).start();
	}

	public void startLoading() {
		
	}
}
