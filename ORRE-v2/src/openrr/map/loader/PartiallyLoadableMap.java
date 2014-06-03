package openrr.map.loader;

import openrr.map.Map;
import openrr.map.MapTile;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableMap implements Finalizable {

	private MapTile[][] tileMap;
	private MapTexturePack texturePack;
	private Map map;

	public PartiallyLoadableMap(MapTile[][] tileMap, MapTexturePack texturePack) {
		this.tileMap = tileMap;
		this.texturePack = texturePack;
	}

	public Resource finalizeResource() {
		texturePack.finalizeTextures();
		Map map = new Map(tileMap, texturePack);
		map.buildAll();
		this.map = map;
		return new Resource(ResourceType.map, "MAP", Map.class, map);
	}

	public SceneNode createSceneNode() {
		return map.createSceneNode();
	}
}
