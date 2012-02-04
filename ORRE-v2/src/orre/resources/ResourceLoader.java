package orre.resources;

import orre.gui.LoadingScreenDrawer;

public class ResourceLoader {
	
	private ResourceCache resourceCache;
	private LoadingScreenDrawer loadingScreenDrawer = null;
	private ProgressTracker progressTracker;
	private ResourceQueue resourceQueue;
	private boolean hasStartedParsing = false;
	private boolean hasStartedLoading = false;
	private ResourceFinalizer resourceFinalizer;
	
	public ResourceLoader()
	{
		this.resourceCache = new ResourceCache();
		this.progressTracker = new ProgressTracker();
		this.resourceQueue = new ResourceQueue(this.progressTracker, this.resourceCache, this);
		this.resourceFinalizer = new ResourceFinalizer(this.resourceQueue, this.resourceCache);
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
	
	public void enqueueResourceFileToBeLoaded(String src, ResourceFile resourceListFile)
	{
		this.resourceQueue.enqueueResourceFile(src, resourceListFile);
	}

	public boolean isFinished() {
		return this.progressTracker.isFinished() && this.resourceQueue.finalizableQueueIsEmpty() && this.hasStartedLoading;
	}
}
