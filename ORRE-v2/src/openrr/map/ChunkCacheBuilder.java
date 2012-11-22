package openrr.map;

public class ChunkCacheBuilder {
	
	private static final int CHUNK_WIDTH_TILES = 5;
	private static final int CHUNK_HEIGHT_TILES = 5;

	public static ChunkCache buildChunkCache(MapTile[][] tileMap, int tileMapWidth, int tileMapHeight) {
		int chunkMapWidth = (int) Math.ceil(tileMap.length / CHUNK_WIDTH_TILES);
		int chunkMapHeight = (int) Math.ceil(tileMap[0].length / CHUNK_HEIGHT_TILES);
		
		Chunk[][] chunkMap = new Chunk[chunkMapWidth][chunkMapHeight];
		ChunkCache cache = new ChunkCache(chunkMap);
		
		for(int chunkX = 0; chunkX < chunkMapWidth; chunkX++) {
			for(int chunkY = 0; chunkY < chunkMapHeight; chunkY++) {
				int remainingTilesX = tileMapWidth - CHUNK_WIDTH_TILES*chunkX;
				int remainingTilesY = tileMapHeight - CHUNK_HEIGHT_TILES*chunkY;
				
				int chunkWidth = Math.min(CHUNK_WIDTH_TILES, remainingTilesX);
				int chunkHeight = Math.min(CHUNK_HEIGHT_TILES, remainingTilesY);
				MapTile[][] chunkTiles = new MapTile[chunkWidth][chunkHeight];
				
				int chunkStartX = CHUNK_WIDTH_TILES*chunkX;
				int chunkStartY = CHUNK_HEIGHT_TILES*chunkY;
				
				for(int i = 0; i < chunkWidth; i++) {
					for(int j = 0; j < chunkHeight; j++) {
						chunkTiles[i][j] = tileMap[chunkStartX + i][chunkStartY + j];
					}
				}
				chunkMap[chunkX][chunkY] = new Chunk(chunkTiles, chunkX, chunkY);
			}
		}
		
		return cache;
	}

}
