package orre.resources;

import orre.util.Queue;

public class ResourceFileParsingThread extends Thread{

	private Queue<String> remainingItemsQueue;

	public ResourceFileParsingThread(Queue<String> itemsToLoadQueue) {
		this.remainingItemsQueue = itemsToLoadQueue;
	}
	
	public void run()
	{
		System.out.println("parsing file: " + remainingItemsQueue.dequeue());
	}

}
