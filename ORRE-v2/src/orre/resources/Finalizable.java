package orre.resources;

import orre.sceneGraph.SceneNode;

public abstract class Finalizable {
	public void finalizeAndSendToCache()
	{
		this.finalizeResource();
	}
	
	public abstract void finalizeResource();
	public abstract void addToCache(ResourceCache cache);
}
