package openrr.map;

import orre.sceneGraph.SceneNode;

public class MapGeometryCache {
	private final MapTile[][] tileMap;
	private SceneNode rootNode;
	private MapGeometryBuffer buffer;

	public MapGeometryCache(MapTile[][] tileMap) {
		this.tileMap = tileMap;
	}
	
	public void update() {
		
	}
	
	public SceneNode getSceneNode() {
		return rootNode;
	}

	public void buildAll() {
		this.buffer = MapBuilder.buildMap(tileMap);
	}
}
