package openrr.map;

import java.util.Arrays;

public class ChunkCacheBuilder {
	


	public static ChunkCache buildChunkCache(MapTile[][] tileMap, int tileMapWidth, int tileMapHeight) {
		int chunkMapWidth = (int) Math.ceil(tileMap.length / Chunk.CHUNK_WIDTH_TILES);
		int chunkMapHeight = (int) Math.ceil(tileMap[0].length / Chunk.CHUNK_HEIGHT_TILES);
		
		Chunk[][] chunkMap = new Chunk[chunkMapWidth][chunkMapHeight];
		ChunkCache cache = new ChunkCache(chunkMap);
		
		for(int chunkX = 0; chunkX < chunkMapWidth; chunkX++) {
			for(int chunkY = 0; chunkY < chunkMapHeight; chunkY++) {
				int remainingTilesX = tileMapWidth - Chunk.CHUNK_WIDTH_TILES*chunkX;
				int remainingTilesY = tileMapHeight - Chunk.CHUNK_HEIGHT_TILES*chunkY;
	
				int chunkWidth = Math.min(Chunk.CHUNK_WIDTH_TILES, remainingTilesX);
				int chunkHeight = Math.min(Chunk.CHUNK_HEIGHT_TILES, remainingTilesY);
				MapTile[][] chunkTiles = new MapTile[chunkWidth][chunkHeight];
				
				int chunkStartX = Chunk.CHUNK_WIDTH_TILES*chunkX;
				int chunkStartY = Chunk.CHUNK_HEIGHT_TILES*chunkY;
				
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
