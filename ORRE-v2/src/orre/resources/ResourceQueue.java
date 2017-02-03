package orre.resources;

import java.util.HashMap;

import orre.threads.ResourceLoadingThread;
import orre.util.Queue;

public class ResourceQueue {
	private static final int NUMBER_OF_LOADING_THREADS = 3;
	
	private Queue<Resource> filesToLoadQueue;
	private Queue<Completable> resourceCompletionQueue;

	private final ProgressTracker tracker;
	private final HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders;
	
	private final ResourceLoadingThread[] loadingThreads = new ResourceLoadingThread[NUMBER_OF_LOADING_THREADS];
	
	public ResourceQueue(ProgressTracker tracker, HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders)
	{
		this.filesToLoadQueue = new Queue<Resource>();
		this.resourceCompletionQueue = new Queue<Completable>();
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

	public synchronized void enqueueCompletable(Resource completable, IncompleteResourceObject<?> resourceObject)
	{
		synchronized(resourceCompletionQueue) {
			completable.updateState(ResourceState.AWAITING_COMPLETION);
			this.resourceCompletionQueue.enqueue(new Completable(completable, resourceObject));
		}
	}
	
	public synchronized Completable getNextCompletable()
	{
		synchronized(resourceCompletionQueue) {
			return this.resourceCompletionQueue.dequeue();
		}
	}
	
	public synchronized Resource getNextEnqueuedFileToLoad()
	{
		return this.filesToLoadQueue.dequeue();
	}
	
	public boolean finalizableQueueIsEmpty(){
		return this.resourceCompletionQueue.isEmpty();
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
