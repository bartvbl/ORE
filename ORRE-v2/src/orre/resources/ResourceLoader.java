package orre.resources;

import java.util.HashMap;

import orre.animation.AnimationLoader;
import orre.gui.LoadingScreenDrawer;
import orre.resources.loaders.TextureLoader;

public class ResourceLoader {
	
	private LoadingScreenDrawer loadingScreenDrawer = null;
	private ProgressTracker progressTracker;
	private ResourceQueue resourceQueue;
	private boolean hasStartedParsing = false;
	private boolean hasStartedLoading = false;
	private ResourceFinalizer resourceFinalizer;
	
	//static to allow multiple ResourceLoaders to exist that share registered loaders.
	private static final HashMap<ResourceType, ResourceTypeLoader> loaders = new HashMap<ResourceType, ResourceTypeLoader>();
	
	static {
		//initialise the loaders map when this class is first loaded.
		loaders.put(ResourceType.ANIMATION_FILE, new AnimationLoader());
		loaders.put(ResourceType.TEXTURE_FILE, new TextureLoader());
	}
	
	public ResourceLoader(ResourceCache cache)
	{
		this.progressTracker = new ProgressTracker();
		this.resourceQueue = new ResourceQueue(this.progressTracker, this, cache);
		this.resourceFinalizer = new ResourceFinalizer(this.resourceQueue, cache);
	}
	
	public void registerStartedLoading()
	{
		this.hasStartedLoading = true;
	}
	
	public void update()
	{
		if(!hasStartedParsing)
		{
			hasStartedParsing = true;
			this.resourceQueue.parseResourceFiles();
		} else if(this.hasStartedLoading){
			this.resourceFinalizer.doFinalizations();
		}
		if(this.loadingScreenDrawer != null)
		{
			this.loadingScreenDrawer.draw(this.progressTracker.getProgress());
		}
	}
	
	public void setLoadingScreen(LoadingScreenDrawer loadingScreen)
	{
		this.loadingScreenDrawer = loadingScreen;
	}

	public boolean isFinished() {
		return this.progressTracker.isFinished() && this.resourceQueue.finalizableQueueIsEmpty() && this.hasStartedLoading;
	}

	public void enqueueResourceFileToBeLoaded(UnloadedResource resource) {
		this.resourceQueue.enqueueResourceFile(resource);
	}
	
	public void registerResourceTypeLoader(ResourceType type, ResourceTypeLoader loader) {
		loaders.put(type, loader);
	}
}
