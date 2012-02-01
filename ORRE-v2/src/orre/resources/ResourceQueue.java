package orre.resources;

import org.dom4j.Node;

import orre.util.Queue;

public class ResourceQueue {
	private static final int NUMBER_OF_LOADING_THREADS = 3;
	
	private Queue<String> itemsToLoadQueue;
	private Queue<ResourceFile> itemsTypeQueue;
	
	private Queue<FileToLoad> filesToLoadQueue;
	
	public ResourceQueue()
	{
		this.itemsToLoadQueue = new Queue<String>();
		this.itemsTypeQueue = new Queue<ResourceFile>();
		
		this.filesToLoadQueue = new Queue<FileToLoad>();
	}
	
	public void enqueueResourceFile(String src, ResourceFile resourceListFile) {
		this.itemsToLoadQueue.enqueue(src);
		this.itemsTypeQueue.enqueue(resourceListFile);
	}

	public void parseResourceFiles() {
		new ResourceFileParsingThread(this, this.itemsToLoadQueue, this.itemsTypeQueue).start();
	}
	
	public void enqueueNodeForLoading(FileToLoad fileToLoad)
	{
		this.filesToLoadQueue.enqueue(fileToLoad);
	}

	public void startLoading() {
		for(int i = 0; i < NUMBER_OF_LOADING_THREADS; i++)
		{
			new ResourceLoadingThread().start();
		}
	}
	
	public synchronized FileToLoad getNextEnqueuedFileToLoad()
	{
		return this.filesToLoadQueue.dequeue();
	}
}
