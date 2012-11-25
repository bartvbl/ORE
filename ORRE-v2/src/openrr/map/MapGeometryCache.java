package openrr.map;

import orre.sceneGraph.SceneNode;

public class MapGeometryCache {
	private MapTile[][] timeMap;
	private SceneNode rootNode;

	public MapGeometryCache(MapTile[][] tileMap) {
		this.timeMap = tileMap;
	}
	
	public void rebuild() {
		
	}
	
	public SceneNode getSceneNode() {
		return rootNode;
	}
}
