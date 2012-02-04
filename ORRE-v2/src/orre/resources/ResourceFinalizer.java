package orre.resources;

import java.util.ArrayList;

import org.lwjgl.util.Timer;

import orre.util.Queue;

public class ResourceFinalizer {
	private Queue<Finalizable> finalizationQueue = new Queue<Finalizable>();

	private Timer finalizationTimer;
	private ResourceQueue resourceQueue;
	private ResourceCache resourceCache;

	private static final float ALLOWED_TIME_FOR_FINALIZATIONS = 0.014f;
	
	public ResourceFinalizer(ResourceQueue resourceQueue, ResourceCache cache) {
		this.resourceQueue = resourceQueue;
		this.finalizationTimer = new Timer();
		this.resourceCache = cache;
	}
	
	public void doFinalizations()
	{
		Timer.tick();
		this.finalizationTimer.reset();
		while(this.finalizationTimer.getTime() < ALLOWED_TIME_FOR_FINALIZATIONS)
		{
			Finalizable resourceToFinalize = this.resourceQueue.getNextFinalizable();
			if(resourceToFinalize == null)
			{
				break;
			}
			resourceToFinalize.finalizeResource(this.resourceCache);
			Timer.tick();
		}
	}
}
