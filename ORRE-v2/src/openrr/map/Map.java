package openrr.map;

import orre.sceneGraph.SceneNode;

public class Map {
	
	private final ChunkCache chunkCache;

	public Map(MapTile[][] tileMap) {
		this.chunkCache = ChunkCacheBuilder.buildChunkCache(tileMap);
	}

	public SceneNode getSceneNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
