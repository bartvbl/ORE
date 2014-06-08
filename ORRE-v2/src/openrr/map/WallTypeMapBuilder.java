package openrr.map;

import orre.geom.Dimension2D;

public class WallTypeMapBuilder {

	public static WallType[][] buildWallTypeMap(boolean[][] vertexHeights, Dimension2D mapSize) {
		int mapWidth = mapSize.width;
		int mapHeight = mapSize.height;
		WallType[][] wallTypeMap = new WallType[mapWidth][mapHeight];
		boolean[][] tileVertexHeights = new boolean[2][2];
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				tileVertexHeights[0][0] = vertexHeights[x + 0][y + 0];
				tileVertexHeights[1][0] = vertexHeights[x + 1][y + 0];
				tileVertexHeights[1][1] = vertexHeights[x + 1][y + 1];
				tileVertexHeights[0][1] = vertexHeights[x + 0][y + 1];
				
				wallTypeMap[x][y] = WallTypeClassifier.classifyTile(tileVertexHeights);
			}
		}
		return wallTypeMap;
	}

}
