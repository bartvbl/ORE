package orre.resources;

public abstract class Finalizable {
	protected ResourceCache destinationCache = null;
	
	public void setDestinationCache(ResourceCache destinationCache)
	{
		this.destinationCache = destinationCache;
	}
	
	public void finalizeAndSendToCache()
	{
		this.finalizeResource();
	}
	
	public abstract void finalizeResource();
}
