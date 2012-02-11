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

	private ResourceLoader resourceLoader;
	
	public ResourceQueue(ProgressTracker tracker, ResourceLoader loader)
	{
		this.itemsToLoadQueue = new Queue<String>();
		this.itemsTypeQueue = new Queue<ResourceFile>();
		this.filesToLoadQueue = new Queue<FileToLoad>();
		this.resourcesToFinalizeQueue = new Queue<Finalizable>();
		this.resourceLoader = loader;
		
		this.tracker = tracker;
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
			new ResourceLoadingThread(this, this.tracker).start();
		}
		this.resourceLoader.registerStartedLoading();
	}
	
	public synchronized void enqueueResourceForFinalization(Finalizable finalizable)
	{
		this.resourcesToFinalizeQueue.enqueue(finalizable);
	}
	
	public synchronized Finalizable getNextFinalizable()
	{
		return this.resourcesToFinalizeQueue.dequeue();
	}
	
	public synchronized FileToLoad getNextEnqueuedFileToLoad()
	{
		return this.filesToLoadQueue.dequeue();
	}
	
	public boolean finalizableQueueIsEmpty(){
		return this.resourcesToFinalizeQueue.isEmpty();
	}
}
