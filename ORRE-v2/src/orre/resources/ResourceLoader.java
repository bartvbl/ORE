package orre.resources;

import orre.gui.LoadingScreenDrawer;

public class ResourceLoader {
	
	private ResourceCache resourceCache;
	private LoadingScreenDrawer loadingScreenDrawer = null;
	private ProgressTracker progressTracker;
	private ResourceQueue resourceQueue;
	private boolean hasStartedParsing = false;
	private boolean hasStartedLoading = false;
	
	public ResourceLoader()
	{
		this.resourceCache = new ResourceCache();
		this.progressTracker = new ProgressTracker();
		this.resourceQueue = new ResourceQueue(this.progressTracker, this.resourceCache);
	}
	
	public void update()
	{
		if(!hasStartedParsing)
		{
			hasStartedParsing = true;
			this.resourceQueue.parseResourceFiles();
		} else if(!hasStartedLoading && hasStartedParsing){
			hasStartedLoading = true;
			this.resourceQueue.startLoading();
		} else {
			
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
		return this.progressTracker.isFinished();
	}
}
