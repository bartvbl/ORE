package openrr.map;

public class WallTypeClassifier {

	public static WallType classifyTile(boolean[][] vertexHeights, int x, int y) {
		int numHighPoints = countHighPoints(vertexHeights, x, y);
		if(numHighPoints == 0) return WallType.ground;
		if(numHighPoints == 1) return WallType.wallCorner;
		if(numHighPoints == 3) return WallType.invertedCorner;
		if(numHighPoints == 4) return WallType.roof;
		if(numHighPoints == 2) {
			return distinguishCreviceAndWall(vertexHeights, x, y);
		}
		return WallType.ground;
	}

	private static WallType distinguishCreviceAndWall(
			boolean[][] vertexHeights, int x, int y) {
		boolean creviceCondition1 = (vertexHeights[x + 0][y + 0] && vertexHeights[x + 1][y + 1]);
		boolean creviceCondition2 = (vertexHeights[x + 1][y + 0] && vertexHeights[x + 0][y + 1]);
		if(creviceCondition1 || creviceCondition2) {
			return WallType.crevice;
		} else {
			return WallType.wall;
		}
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
