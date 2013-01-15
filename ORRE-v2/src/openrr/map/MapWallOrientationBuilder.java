package openrr.map;

import orre.geom.Dimension2D;

public class MapWallOrientationBuilder {
	public static Orientation[][] buildOrientationMap(boolean[][] vertexHeights, WallType[][] wallType, Dimension2D mapSize) {
		int mapWidth = vertexHeights.length;
		int mapHeight = vertexHeights[0].length;
		Orientation[][] orientationMap = new Orientation[mapWidth][mapHeight];
		
		
		return orientationMap;
	}

}
