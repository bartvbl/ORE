package openrr.map;

import orre.resources.ResourceCache;
import orre.resources.loaders.map.MapTexturePack;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;
import orre.util.MathUtil;

public class Map {
	private final MapGeometryCache cache;
	private final MapTile[][] tileMap;
	private final MapTexturePack texturePack;
	
	public Map(MapTile[][] tileMap, MapTexturePack texturePack) {
		this.tileMap = tileMap;
		this.texturePack = texturePack;
		this.cache = new MapGeometryCache(tileMap, texturePack);
	}

	public SceneNode createSceneNode() {
		return cache.getSceneNode();
	}
	
	public void tick() {
		
	}

	public void buildAll() {
		this.cache.buildAll();
	}

	public float getTileHeightAt(int x, int y) {
		x = MathUtil.clamp(x, 0, tileMap.length - 1);
		y = MathUtil.clamp(y, 0, tileMap[0].length - 1);
		return tileMap[x][y].tileHeight[0][0];
	}
}
