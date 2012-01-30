package orre.resources;

import java.util.ArrayList;

import orre.gui.LoadingScreenDrawer;
import orre.util.Queue;

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
		this.resourceQueue = new ResourceQueue();
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
	
	public void enqueueResourceFileToBeLoaded(String src)
	{
		this.resourceQueue.enqueueResourceFile(src);
	}

	public boolean isFinished() {
		return this.progressTracker.isFinished();
	}
}
