package orre.resources;

import org.lwjgl.util.Timer;

public class ResourceFinalizer {
	private Timer finalizationTimer;
	private ResourceQueue resourceQueue;
	private final ResourceCache cache;

	private static final float ALLOWED_TIME_FOR_FINALIZATIONS = 0.014f;
	
	public ResourceFinalizer(ResourceQueue resourceQueue, ResourceCache cache) {
		this.resourceQueue = resourceQueue;
		this.cache = cache;
		this.finalizationTimer = new Timer();
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
			Resource resource = resourceToFinalize.finalizeResource();
			if(resource != null) {
				cache.addResource(resource);
			}
			Timer.tick();
		}
	}
}