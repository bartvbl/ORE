package orre.resources;

import org.lwjgl.util.Timer;

public class ResourceFinalizer {
	private Timer finalizationTimer;
	private ResourceQueue resourceQueue;

	private static final float ALLOWED_TIME_FOR_FINALIZATIONS = 0.014f;
	
	public ResourceFinalizer(ResourceQueue resourceQueue) {
		this.resourceQueue = resourceQueue;
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
			resourceToFinalize.finalizeResource();
			resourceToFinalize.addToCache();
			Timer.tick();
		}
	}
}
