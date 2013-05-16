package orre.resources;

import orre.sceneGraph.SceneNode;

public abstract class PartiallyLoadable {
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
	public abstract SceneNode createSceneNode();
	public abstract void addToCache();
}
