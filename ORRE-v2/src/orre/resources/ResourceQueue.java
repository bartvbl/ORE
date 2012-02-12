package orre.resources;

import org.dom4j.Node;

import orre.threads.ResourceFileParsingThread;
import orre.threads.ResourceLoadingThread;
import orre.util.Queue;

public class ResourceQueue {
	private static final int NUMBER_OF_LOADING_THREADS = 3;
	
	private Queue<FileToLoad> itemsToLoadQueue;
	private Queue<FileToLoad> filesToLoadQueue;
	private Queue<Finalizable> resourcesToFinalizeQueue;

	private ProgressTracker tracker;

	private ResourceLoader resourceLoader;
	
	public ResourceQueue(ProgressTracker tracker, ResourceLoader loader)
	{
		this.itemsToLoadQueue = new Queue<FileToLoad>();
		this.filesToLoadQueue = new Queue<FileToLoad>();
		this.resourcesToFinalizeQueue = new Queue<Finalizable>();
		this.resourceLoader = loader;
		
		this.tracker = tracker;
	}
	
	public void enqueueResourceFile(String src, ResourceFile fileType, ResourceCache destinationCache) {
		this.itemsToLoadQueue.enqueue(new FileToLoad(fileType, destinationCache, src));
	}

	public void parseResourceFiles() {
		new ResourceFileParsingThread(this, this.itemsToLoadQueue).start();
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
