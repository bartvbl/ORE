package orre.resources.loaders.map;

import openrr.map.Map;
import openrr.map.MapTile;
import orre.resources.Finalizable;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableMap extends Finalizable {

	private MapTile[][] tileMap;
	private MapTexturePack texturePack;
	private Map map;

	public PartiallyLoadableMap(MapTile[][] tileMap, MapTexturePack texturePack) {
		this.tileMap = tileMap;
		this.texturePack = texturePack;
	}

	public void finalizeResource() {
		texturePack.finalizeTextures();
		Map map = new Map(tileMap, texturePack);
		map.buildAll();
		this.map = map;
	}

	public SceneNode createSceneNode() {
		return map.createSceneNode();
	}

	public void addToCache() {
		this.destinationCache.setMap(map);
	}

}
