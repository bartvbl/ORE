package orre.resources;

import org.lwjgl.util.Timer;

public class ResourceCompleter {
	private Timer finalizationTimer;
	private ResourceQueue resourceQueue;
	private final ResourceLoader loader;

	private static final float ALLOWED_TIME_FOR_FINALIZATIONS = 1.0f / 70.0f;
	
	public ResourceCompleter(ResourceQueue resourceQueue, ResourceLoader loader) {
		this.resourceQueue = resourceQueue;
		this.finalizationTimer = new Timer();
		this.loader = loader;
	}
	
	public void completeResources()
	{
		Timer.tick();
		this.finalizationTimer.reset();
		while(this.finalizationTimer.getTime() < ALLOWED_TIME_FOR_FINALIZATIONS)
		{
			Completable resourceToComplete = this.resourceQueue.getNextCompletable();
			try {
				ResourceTypeLoader typeLoader = loader.getLoaderFor(resourceToComplete.resource.type);
				ResourceObject<?> completedObject = typeLoader.completeResource(resourceToComplete.incompleteObject);
				resourceToComplete.resource.content = completedObject;
				resourceToComplete.resource.updateState(ResourceState.LOADED);
			} catch(Exception e) {
				throw new RuntimeException("Completion of resource " + resourceToComplete + " failed.", e);
			}
			Timer.tick();
		}
	}
}