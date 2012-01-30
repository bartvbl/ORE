package orre.resources;

import java.util.ArrayList;

import orre.gui.LoadingScreenDrawer;

public class ResourceLoader {
	private int totalItemsToLoad = 0;
	private int totalItemsLoaded = 0;
	private LoadingScreenDrawer loadingScreenDrawer = null;
	private ArrayList<Finalizable> finalizationQueue = new ArrayList<Finalizable>();
	private ResourceCache resourceCache;
	
	public ResourceLoader()
	{
		this.resourceCache = new ResourceCache();
	}
	
	public void update()
	{
		
		if(this.loadingScreenDrawer != null)
		{
			this.loadingScreenDrawer.draw((float) this.totalItemsLoaded / (float) this.totalItemsToLoad);
		}
	}
	
	public void setLoadingScreen(LoadingScreenDrawer loadingScreen)
	{
		this.loadingScreenDrawer = loadingScreen;
	}
	
	public void incrementLoadedItemsCount()
	{
		this.totalItemsLoaded++;
	}
	
	public synchronized void addItemsToTotalItemsToLoad(int numberOfItems)
	{
		this.totalItemsToLoad += numberOfItems;
	}
	
	public synchronized void enqueueObjectForFinalization(Finalizable object)
	{
		synchronized(this.finalizationQueue)
		{
			this.finalizationQueue.add(object);
		}
	}
}
