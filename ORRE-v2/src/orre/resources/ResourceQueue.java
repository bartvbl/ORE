package orre.resources;

import java.io.File;
import java.util.HashMap;

import orre.threads.ResourceFileParsingThread;
import orre.threads.ResourceLoadingThread;
import orre.util.Queue;

public class ResourceQueue {
	private static final int NUMBER_OF_LOADING_THREADS = 3;
	
	private Queue<UnloadedResource> itemsToLoadQueue;
	private Queue<UnloadedResource> filesToLoadQueue;
	private Queue<Finalizable> resourcesToFinalizeQueue;

	private final ProgressTracker tracker;
	private final ResourceLoader resourceLoader;
	
	public ResourceQueue(ProgressTracker tracker, ResourceLoader loader)
	{
		this.itemsToLoadQueue = new Queue<UnloadedResource>();
		this.filesToLoadQueue = new Queue<UnloadedResource>();
		this.resourcesToFinalizeQueue = new Queue<Finalizable>();
		this.resourceLoader = loader;
		
		this.tracker = tracker;
	}
	
	public void parseResourceFiles(HashMap<ResourceType, ResourceTypeLoader> loaders) {
		new ResourceFileParsingThread(this, this.itemsToLoadQueue, loaders).start();
	}
	
	public void enqueueResourceFile(UnloadedResource resource) {
		this.itemsToLoadQueue.enqueue(resource);
	}
	
	public void enqueueNodeForLoading(UnloadedResource fileToLoad)
	{
		this.filesToLoadQueue.enqueue(fileToLoad);
		this.tracker.addFileToLoad();
	}

	public void startLoading(HashMap<ResourceType, ResourceTypeLoader> registeredLoaders) {
		for(int i = 0; i < NUMBER_OF_LOADING_THREADS; i++)
		{
			new ResourceLoadingThread(this, this.tracker, registeredLoaders).start();
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
	
	public synchronized UnloadedResource getNextEnqueuedFileToLoad()
	{
		return this.filesToLoadQueue.dequeue();
	}
	
	public boolean finalizableQueueIsEmpty(){
		return this.resourcesToFinalizeQueue.isEmpty();
	}
}
