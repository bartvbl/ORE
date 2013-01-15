package openrr.map;

public class WallTypeMapBuilder {

	public static WallType[][] buildWallTypeMap(boolean[][] vertexHeights) {
		int mapWidth = vertexHeights.length - 1;
		int mapHeight = vertexHeights[0].length - 1;
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
