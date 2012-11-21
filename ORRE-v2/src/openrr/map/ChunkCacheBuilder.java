package openrr.map;

public class ChunkCacheBuilder {
	
	private static final int CHUNK_WIDTH_TILES = 5;
	private static final int CHUNK_HEIGHT_TILES = 5;

	public static ChunkCache buildChunkCache(MapTile[][] tileMap) {
		int chunkMapWidth = (int) Math.ceil(tileMap.length / CHUNK_WIDTH_TILES);
		int chunkMapHeight = (int) Math.ceil(tileMap[0].length / CHUNK_HEIGHT_TILES);
		
		Chunk[][] chunkMap = new Chunk[chunkMapWidth][chunkMapHeight];
		ChunkCache cache = new ChunkCache(chunkMap);
		
		
		
		return cache;
	}

}
