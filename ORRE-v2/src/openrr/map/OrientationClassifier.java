package openrr.map;

public class OrientationClassifier {
	public static Orientation classifyTile(boolean[][] isWallNeighbourhood2x2, WallType wallType) {
		if(wallType == WallType.ground) 		 	 return classifyGround(isWallNeighbourhood2x2);
		else if(wallType == WallType.roof) 			 return classifyRoof(isWallNeighbourhood2x2);
		else if(wallType == WallType.wallCorner) 	 return classifyWallCorner(isWallNeighbourhood2x2);
		else if(wallType == WallType.wall) 			 return classifyWall(isWallNeighbourhood2x2);
		else if(wallType == WallType.invertedCorner) return classifyInvertedCorner(isWallNeighbourhood2x2);
		else if(wallType == WallType.crevice) 		 return classifyCrevice(isWallNeighbourhood2x2);
		return Orientation.north;
	}

	private static Orientation classifyGround(boolean[][] isWallNeighbourhood2x2) {
		return Orientation.north;
	}

	private static Orientation classifyRoof(boolean[][] isWallNeighbourhood2x2) {
		return Orientation.north;
	}

	private static Orientation classifyWallCorner(boolean[][] isWallNeighbourhood2x2) {
		if(isWallNeighbourhood2x2[0][0] == true) return Orientation.west;
		if(isWallNeighbourhood2x2[1][0] == true) return Orientation.south;
		if(isWallNeighbourhood2x2[1][1] == true) return Orientation.east;
		if(isWallNeighbourhood2x2[0][1] == true) return Orientation.north;
		return Orientation.north;
	}

	private static Orientation classifyWall(boolean[][] isWallNeighbourhood2x2) {
		if(isWallNeighbourhood2x2[0][0] == false) {
			if(isWallNeighbourhood2x2[1][0] == false) {
				return Orientation.north;
			} else {
				return Orientation.east;
			}
		} else {
			if(isWallNeighbourhood2x2[1][0] == true) {
				return Orientation.south;
			} else {
				return Orientation.west;
			}
		}
	}

	private static Orientation classifyInvertedCorner(boolean[][] isWallNeighbourhood2x2) {
		if(isWallNeighbourhood2x2[0][0] == false) return Orientation.east;
		if(isWallNeighbourhood2x2[0][1] == false) return Orientation.south;
		if(isWallNeighbourhood2x2[1][1] == false) return Orientation.west;
		if(isWallNeighbourhood2x2[1][0] == false) return Orientation.north;
		return Orientation.north;
	}

	private static Orientation classifyCrevice(boolean[][] isWallNeighbourhood2x2) {
		//	01	-> north	10	-> west
		//  10				01
		if(isWallNeighbourhood2x2[0][0] == true) return Orientation.north;
		return Orientation.west;
	}
}