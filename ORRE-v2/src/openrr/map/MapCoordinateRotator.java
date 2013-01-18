package openrr.map;

public class MapCoordinateRotator {

	private static final double[][] coordinates6x3 = new double[6][3];
	
	public static double[][] generateRotatedTileCoordinates(int x, int y, double[][] tileHeight, Orientation orientation) {
		switch(orientation) {
			default:
			case north:
				generateNorthCoordinates(x, y, tileHeight);
				break;
			case east:
				generateEastCoordinates(x, y, tileHeight);
				break;
			case south:
				generateSouthCoordinates(x, y, tileHeight);
				break;
			case west:
				generateWestCoordinates(x, y, tileHeight);
				break;
		}
		return coordinates6x3;
	}

	private static void generateNorthCoordinates(int x, int y, double[][] tileHeight) {
		setBottomLeftCoordinateAt(x, y, tileHeight, 0);
		setBottomRightCoordinateAt(x, y, tileHeight, 1);
		setTopRightCoordinateAt(x, y, tileHeight, 2);

		setBottomLeftCoordinateAt(x, y, tileHeight, 3);
		setTopRightCoordinateAt(x, y, tileHeight, 4);
		setTopLeftCoordinateAt(x, y, tileHeight, 5);
	}

	private static void generateEastCoordinates(int x, int y, double[][] tileHeight) {
		setBottomRightCoordinateAt(x, y, tileHeight, 0);
		setTopRightCoordinateAt(x, y, tileHeight, 1);
		setTopLeftCoordinateAt(x, y, tileHeight, 2);
		
		setBottomRightCoordinateAt(x, y, tileHeight, 3);
		setTopLeftCoordinateAt(x, y, tileHeight, 4);
		setBottomLeftCoordinateAt(x, y, tileHeight, 5);
	}

	private static void generateSouthCoordinates(int x, int y,
			double[][] tileHeight) {
		setTopRightCoordinateAt(x, y, tileHeight, 0);
		setTopLeftCoordinateAt(x, y, tileHeight, 1);
		setBottomLeftCoordinateAt(x, y, tileHeight, 2);
		
		setTopRightCoordinateAt(x, y, tileHeight, 3);
		setBottomLeftCoordinateAt(x, y, tileHeight, 4);
		setBottomRightCoordinateAt(x, y, tileHeight, 5);
	}

	private static void generateWestCoordinates(int x, int y, double[][] tileHeight) {
		setTopLeftCoordinateAt(x, y, tileHeight, 0);
		setBottomLeftCoordinateAt(x, y, tileHeight, 1);
		setBottomRightCoordinateAt(x, y, tileHeight, 2);
		
		setTopLeftCoordinateAt(x, y, tileHeight, 3);
		setBottomRightCoordinateAt(x, y, tileHeight, 4);
		setTopRightCoordinateAt(x, y, tileHeight, 5);
	}

	
	private static void setBottomLeftCoordinateAt(int x, int y, double[][] tileHeight, int index) {
		coordinates6x3[index][0] = x;
		coordinates6x3[index][1] = y;
		coordinates6x3[index][2] = tileHeight[0][0];
	}
	
	private static void setTopLeftCoordinateAt(int x, int y, double[][] tileHeight, int index) {
		coordinates6x3[index][0] = x;
		coordinates6x3[index][1] = y + 1;
		coordinates6x3[index][2] = tileHeight[0][1];
	}
	
	private static void setBottomRightCoordinateAt(int x, int y, double[][] tileHeight, int index) {
		coordinates6x3[index][0] = x + 1;
		coordinates6x3[index][1] = y;
		coordinates6x3[index][2] = tileHeight[1][0];
	}
	
	private static void setTopRightCoordinateAt(int x, int y, double[][] tileHeight, int index) {
		coordinates6x3[index][0] = x + 1;
		coordinates6x3[index][1] = y + 1;
		coordinates6x3[index][2] = tileHeight[1][1];
	}
}
