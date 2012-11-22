package openrr.map;

import orre.resources.ResourceCache;
import orre.sceneGraph.SceneNode;

public class Map {
	
	private final ChunkCache chunkCache;

	public Map(MapTile[][] tileMap) {
		this.chunkCache = ChunkCacheBuilder.buildChunkCache(tileMap);
	}

	public SceneNode createSceneNode(ResourceCache cache) {
		return chunkCache.createSceneNode(cache);
	}
}
