package orre.resources.loaders.map;

import openrr.map.Map;
import orre.resources.Finalizable;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableMap extends Finalizable {

	private Map map;

	public PartiallyLoadableMap(Map map) {
		this.map = map;
	}

	public void finalizeResource() {}

	public SceneNode createSceneNode() {
		return map.getSceneNode();
	}

	public void addToCache() {
		this.destinationCache.setMap(map);
	}

}
