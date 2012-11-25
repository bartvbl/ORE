package openrr.map;

public class WallTypeMapParser {

	public static WallType[][] createWallTypeMap(boolean[][] vertexHeights, int width, int height) {
		WallType[][] wallTypeMap = new WallType[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				wallTypeMap[x][y] = findWallTypeMap(vertexHeights, x, y);
			}
		}
		return wallTypeMap;
	}

	private static WallType findWallTypeMap(boolean[][] vertexHeights, int x, int y) {
		int numHighPoints = countHighPoints(vertexHeights, x, y);
		if(numHighPoints == 0) return WallType.ground;
		if(numHighPoints == 1) return WallType.wallCorner;
		if(numHighPoints == 3) return WallType.invertedCorner;
		if(numHighPoints == 4) return WallType.roof;
		if(numHighPoints == 2) {
			boolean creviceCondition1 = (vertexHeights[x + 0][y + 0] && vertexHeights[x + 1][y + 1]);
			boolean creviceCondition2 = (vertexHeights[x + 1][y + 0] && vertexHeights[x + 0][y + 1]);
			if(creviceCondition1 || creviceCondition2) {
				return WallType.crevice;
			} else {
				return WallType.wall;
			}
		}
		return WallType.ground;
	}

	private static int countHighPoints(boolean[][] vertexHeights, int x, int y) {
		int highPointCount = 0;
		if(vertexHeights[x + 0][y + 0]) highPointCount++;
		if(vertexHeights[x + 0][y + 1]) highPointCount++;
		if(vertexHeights[x + 1][y + 0]) highPointCount++;
		if(vertexHeights[x + 1][y + 1]) highPointCount++;
		return highPointCount;
	}

}
