package orre.resources;

import java.util.HashMap;

import orre.threads.ResourceLoadingThread;
import orre.util.Queue;

public class ResourceQueue {
	private static final int NUMBER_OF_LOADING_THREADS = 3;
	
	private Queue<UnloadedResource> filesToLoadQueue;
	private Queue<Finalizable> resourcesToFinalizeQueue;

	private final ProgressTracker tracker;
	
	public ResourceQueue(ProgressTracker tracker)
	{
		this.filesToLoadQueue = new Queue<UnloadedResource>();
		this.resourcesToFinalizeQueue = new Queue<Finalizable>();
		this.tracker = tracker;
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

	public boolean isDestroyed() {
		return false;
	}
}
