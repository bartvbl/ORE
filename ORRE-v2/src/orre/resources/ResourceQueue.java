package orre.resources;

import org.dom4j.Node;

import orre.util.Queue;

public class ResourceQueue {
	private static final int NUMBER_OF_LOADING_THREADS = 3;
	
	private Queue<String> itemsToLoadQueue;
	private Queue<ResourceFile> itemsTypeQueue;
	private Queue<FileToLoad> filesToLoadQueue;
	private Queue<Finalizable> resourcesToFinalizeQueue;

	private ProgressTracker tracker;

	private ResourceCache resourceCache;
	
	public ResourceQueue(ProgressTracker tracker, ResourceCache cache)
	{
		this.itemsToLoadQueue = new Queue<String>();
		this.itemsTypeQueue = new Queue<ResourceFile>();
		this.filesToLoadQueue = new Queue<FileToLoad>();
		this.resourcesToFinalizeQueue = new Queue<Finalizable>();
		
		this.tracker = tracker;
		this.resourceCache = cache;
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
		this.tracker.addFileToLoad();
	}

	public void startLoading() {
		for(int i = 0; i < NUMBER_OF_LOADING_THREADS; i++)
		{
			new ResourceLoadingThread(this).start();
		}
	}
	
	public synchronized void queueResourceForFInalization(Finalizable finalizable)
	{
		
	}
	
	public synchronized Finalizable getNextFinalizable()
	{
		return null;
	}
	
	public synchronized FileToLoad getNextEnqueuedFileToLoad()
	{
		return this.filesToLoadQueue.dequeue();
	}
}
