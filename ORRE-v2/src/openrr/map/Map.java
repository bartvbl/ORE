package openrr.map;

import orre.resources.ResourceCache;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class Map {
	private final MapGeometryCache cache;
	private final MapTile[][] tileMap;
	
	public Map(MapTile[][] tileMap) {
		this.tileMap = tileMap;
		this.cache = new MapGeometryCache(tileMap);
	}

	public SceneNode createSceneNode() {
		return cache.getSceneNode();
	}
	
	public void tick() {
		
	}

	public void buildAll() {
		this.cache.buildAll();
	}
}
