package openrr.map;

import orre.resources.ResourceCache;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class Map {
	private MapGeometryCache cache;
	
	public Map(MapTile[][] tileMap) {
		
	}

	public SceneNode createSceneNode() {
		return new EmptySceneNode();
	}
	
	public void tick() {
		
	}
}
