package orre.resources;

import java.util.List;

import org.dom4j.Node;

import orre.util.Queue;

public class ResourceQueue {
	private Queue<String> itemsToLoadQueue;
	private Queue<ResourceFile> itemsTypeQueue;
	
	public ResourceQueue()
	{
		this.itemsToLoadQueue = new Queue<String>();
		this.itemsTypeQueue = new Queue<ResourceFile>();
	}
	
	public void enqueueResourceFile(String src, ResourceFile resourceListFile) {
		this.itemsToLoadQueue.enqueue(src);
		this.itemsTypeQueue.enqueue(resourceListFile);
	}

	public void parseResourceFiles() {
		new ResourceFileParsingThread(this.itemsToLoadQueue, this.itemsTypeQueue).start();
	}
	
	public void enqueueNodeForLoading(Node node, ResourceFile type)
	{
		
	}

	public void startLoading() {
		
	}
}
