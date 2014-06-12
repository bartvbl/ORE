package orre.resources;

import java.util.HashMap;

import orre.threads.ResourceLoadingThread;
import orre.util.Queue;

public class ResourceQueue {
	private static final int NUMBER_OF_LOADING_THREADS = 3;
	
	private Queue<UnloadedResource> filesToLoadQueue;
	private Queue<Finalizable> resourcesToFinalizeQueue;

	private final ProgressTracker tracker;
	private final HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders;
	
	public ResourceQueue(ProgressTracker tracker, HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders)
	{
		this.filesToLoadQueue = new Queue<UnloadedResource>();
		this.resourcesToFinalizeQueue = new Queue<Finalizable>();
		this.tracker = tracker;
		this.registeredLoaders = registeredLoaders;
	}
	
	public void enqueueNodeForLoading(UnloadedResource fileToLoad)
	{
		this.filesToLoadQueue.enqueue(fileToLoad);
		this.tracker.addFileToLoad();
	}

	public void startLoading() {
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

	public Enum<?> getMatchingResourceType(String resourceTypeName) {
		for(Enum<?> loaderType : registeredLoaders.keySet()) {
			if(loaderType.toString().equals(resourceTypeName)) {
				return loaderType;
			}
		}
		throw new IllegalArgumentException();
	}
}
