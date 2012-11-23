package openrr.map;

import orre.resources.ResourceCache;
import orre.sceneGraph.SceneNode;

public class Map {
	
	private final ChunkCache chunkCache;

	public Map(MapTile[][] tileMap) {
		this.chunkCache = ChunkCacheBuilder.buildChunkCache(tileMap, tileMap.length, tileMap[0].length);
	}

	public SceneNode createSceneNode() {
		return chunkCache.createSceneNode();
	}
	
	public void tick() {
		this.chunkCache.update();
	}
}
