package orre.resources.partiallyLoadables;


import orre.resources.Finalizable;
import orre.resources.data.BlueprintModel;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableModel extends Finalizable {
	private BlueprintModel model;

	@Override
	public void finalizeResource() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneNode createSceneNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToCache() {
		this.destinationCache.addModel(this.model);
	}
} 
