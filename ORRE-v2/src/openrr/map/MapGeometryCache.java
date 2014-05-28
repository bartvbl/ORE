package openrr.map;

import openrr.map.loader.MapTexturePack;
import orre.sceneGraph.SceneNode;

public class MapGeometryCache {
	private final MapTile[][] tileMap;
	private SceneNode rootNode;
	private SceneNode buffer;
	private MapTexturePack texturePack;

	public MapGeometryCache(MapTile[][] tileMap, MapTexturePack texturePack) {
		this.tileMap = tileMap;
		this.texturePack = texturePack;
	}
	
	public void update() {
		
	}
	
	public SceneNode getSceneNode() {
		return buffer;
	}

	public void buildAll() {
		this.buffer = MapBuilder.buildMapGeometry(tileMap, texturePack);
	}
}
