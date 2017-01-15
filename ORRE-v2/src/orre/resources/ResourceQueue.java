package orre.resources;

import java.util.HashMap;

import orre.threads.ResourceLoadingThread;
import orre.util.Queue;

public class ResourceQueue {
	private static final int NUMBER_OF_LOADING_THREADS = 3;
	
	private Queue<Resource> filesToLoadQueue;
	private Queue<Finalizable> resourcesToFinalizeQueue;

	private final ProgressTracker tracker;
	private final HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders;
	
	private final ResourceLoadingThread[] loadingThreads = new ResourceLoadingThread[NUMBER_OF_LOADING_THREADS];
	
	public ResourceQueue(ProgressTracker tracker, HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders)
	{
		this.filesToLoadQueue = new Queue<Resource>();
		this.resourcesToFinalizeQueue = new Queue<Finalizable>();
		this.tracker = tracker;
		this.registeredLoaders = registeredLoaders;

		for(int i = 0; i < NUMBER_OF_LOADING_THREADS; i++)
		{
			ResourceLoadingThread thread = new ResourceLoadingThread(this, this.tracker, registeredLoaders);
			thread.start();
			loadingThreads[i] = thread;
		}
	}
	
	public void enqueueNodeForLoading(Resource fileToLoad)
	{
		this.filesToLoadQueue.enqueue(fileToLoad);
		this.tracker.addFileToLoad();
	}

	public synchronized void enqueueResourceForFinalization(Finalizable finalizable)
	{
		this.resourcesToFinalizeQueue.enqueue(finalizable);
	}
	
	public synchronized Finalizable getNextFinalizable()
	{
		return this.resourcesToFinalizeQueue.dequeue();
	}
	
	public synchronized Resource getNextEnqueuedFileToLoad()
	{
		return this.filesToLoadQueue.dequeue();
	}
	
	public boolean finalizableQueueIsEmpty(){
		return this.resourcesToFinalizeQueue.isEmpty();
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
