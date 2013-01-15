package openrr.map;

public class MapWallParser {

	public static boolean[][] exploreNeighbourhood(MapTile[][] tileMap, int x, int y) {
		boolean[][] neighbourhood = new boolean[3][3];
		boolean[][] wallHeightNeighbourhood = new boolean[2][2];
		neighbourhood = exploreNeighbourhood(tileMap, x, y, neighbourhood);
		createWallHeightNeighbourhood(neighbourhood, wallHeightNeighbourhood);
		return wallHeightNeighbourhood;
	}

	private static boolean[][] exploreNeighbourhood(MapTile[][] tileMap, int x, int y, boolean[][] neighbourhood) {
		int width = tileMap.length;
		int height = tileMap[0].length;
		for(int i = 0; i <= 2; i++) {
			for(int j = 0; j <= 2; j++) {
				int xCoordinate = x + i - 1;
				int yCoordinate = y + j - 1;
				if		((xCoordinate) < 0) 		neighbourhood[i][j] = true;
				else if ((xCoordinate) >= width) 	neighbourhood[i][j] = true;
				else if ((yCoordinate) < 0) 		neighbourhood[i][j] = true;
				else if ((yCoordinate) >= height)   neighbourhood[i][j] = true;
				else neighbourhood[i][j] = tileMap[xCoordinate][yCoordinate].isWall();
			}
		}
		return neighbourhood;
	}

	private static void createWallHeightNeighbourhood(boolean[][] neighbourhood, boolean[][] wallHeightNeighbourhood) {
		wallHeightNeighbourhood[0][0] = neighbourhood[1][1] && neighbourhood[0][0] && neighbourhood[0][1] && neighbourhood[1][0];
		wallHeightNeighbourhood[0][1] = neighbourhood[1][1] && neighbourhood[0][1] && neighbourhood[0][2] && neighbourhood[1][2];
		wallHeightNeighbourhood[1][0] = neighbourhood[1][1] && neighbourhood[1][0] && neighbourhood[2][0] && neighbourhood[2][1];
		wallHeightNeighbourhood[1][1] = neighbourhood[1][1] && neighbourhood[2][1] && neighbourhood[2][2] && neighbourhood[1][2];
	}
}
