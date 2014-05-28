package openrr.map;

import openrr.map.loader.MapTexturePack;
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

	public MapTile getTileAt(int x, int y) {
		x = MathUtil.clamp(x, 0, tileMap.length - 1);
		y = MathUtil.clamp(y, 0, tileMap[0].length - 1);
		return tileMap[x][y];
	}

	public double getTileHeightAt(double x, double y) {
		MapTile tile = getTileAt((int)x, (int)y);
		double xOnTile = x - Math.floor(x);
		double yOnTile = y - Math.floor(y);
		if(x > y) {
			double dx = tile.tileHeight[1][0] - tile.tileHeight[0][0];
			double dy = tile.tileHeight[1][1] - tile.tileHeight[1][0];
			return tile.tileHeight[0][0] + xOnTile*dx + yOnTile*dy;
		} else {
			double dx = tile.tileHeight[1][1] - tile.tileHeight[0][1];
			double dy = tile.tileHeight[0][1] - tile.tileHeight[0][0];
			return tile.tileHeight[0][0] + xOnTile*dx + yOnTile*dy;
		}
	}
}
