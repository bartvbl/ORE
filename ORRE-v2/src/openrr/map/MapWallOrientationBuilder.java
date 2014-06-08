package openrr.map;

import orre.geom.Dimension2D;

public class MapWallOrientationBuilder {
	public static Orientation[][] buildOrientationMap(boolean[][] vertexHeights, WallType[][] wallTypeMap, Dimension2D mapSize) {
		Orientation[][] orientationMap = new Orientation[mapSize.width][mapSize.height];
		boolean[][] isWallNeighbourhood2x2 = new boolean[2][2];
		for(int x = 0; x < mapSize.width; x++) {
			for(int y = 0; y < mapSize.height; y++) {
				isWallNeighbourhood2x2[0][0] = vertexHeights[x + 0][y + 0];
				isWallNeighbourhood2x2[1][0] = vertexHeights[x + 1][y + 0];
				isWallNeighbourhood2x2[1][1] = vertexHeights[x + 1][y + 1];
				isWallNeighbourhood2x2[0][1] = vertexHeights[x + 0][y + 1];
				
				orientationMap[x][y] = OrientationClassifier.classifyTile(isWallNeighbourhood2x2, wallTypeMap[x][y]);
			}
		}
		return orientationMap;
	}

}
