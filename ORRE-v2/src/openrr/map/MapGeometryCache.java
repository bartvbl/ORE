package openrr.map;

import orre.geom.vbo.GeometryBuffer;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class MapGeometryCache {
	private final MapTile[][] tileMap;
	private SceneNode rootNode;
	private GeometryBuffer buffer;

	public MapGeometryCache(MapTile[][] tileMap) {
		this.tileMap = tileMap;
	}
	
	public void update() {
		
	}
	
	public SceneNode getSceneNode() {
		return buffer;
	}

	public void buildAll() {
		this.buffer = MapBuilder.buildMapGeometry(tileMap);
	}
}
