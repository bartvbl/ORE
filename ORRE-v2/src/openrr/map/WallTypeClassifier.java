package openrr.map;

public class WallTypeClassifier {

	public static WallType classifyTile(boolean[][] vertexHeights2x2) {
		int numHighPoints = countHighPoints(vertexHeights2x2);
		if(numHighPoints == 0) return WallType.ground;
		if(numHighPoints == 1) return WallType.wallCorner;
		if(numHighPoints == 3) return WallType.invertedCorner;
		if(numHighPoints == 4) return WallType.roof;
		if(numHighPoints == 2) {
			return distinguishCreviceAndWall(vertexHeights2x2);
		}
		return WallType.ground;
	}

	private static WallType distinguishCreviceAndWall(boolean[][] vertexHeights) {
		boolean creviceCondition1 = (vertexHeights[0][0] && vertexHeights[1][1]);
		boolean creviceCondition2 = (vertexHeights[1][0] && vertexHeights[0][1]);
		if(creviceCondition1 || creviceCondition2) {
			return WallType.crevice;
		} else {
			return WallType.wall;
		}
	}

	private static int countHighPoints(boolean[][] vertexHeights) {
		int highPointCount = 0;
		if(vertexHeights[0][0]) highPointCount++;
		if(vertexHeights[0][1]) highPointCount++;
		if(vertexHeights[1][0]) highPointCount++;
		if(vertexHeights[1][1]) highPointCount++;
		return highPointCount;
	}
}
