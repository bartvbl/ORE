package orre.resources;

import java.util.ArrayList;

import orre.util.Queue;

public class ResourceFinalizer {
	private Queue<Finalizable> finalizationQueue = new Queue<Finalizable>();

	private static final float ALLOWED_TIME_FOR_FINALIZATIONS = 0.014f;
	
	public synchronized void enqueueObjectForFinalization(Finalizable object)
	{
		synchronized(this.finalizationQueue)
		{
			this.finalizationQueue.enqueue(object);
		}
	}
	
	
	public void enqueueItemForFinalization(Finalizable item)
	{
		this.finalizationQueue.enqueue(item);
	}
	
	public void doFinalizations()
	{
		
	}
}
