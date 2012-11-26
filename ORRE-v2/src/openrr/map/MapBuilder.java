package openrr.map;

import java.util.Arrays;

public class MapBuilder {

	public static MapGeometryBuffer buildMap(MapTile[][] tileMap) {
		int width = tileMap.length;
		int height = tileMap[0].length;
		int[] indices = generateIndexBuffer(width, height);
		
		double[] vertices = generateVertexBuffer(tileMap); 
		return null;
	}

	

	private static int[] generateIndexBuffer(int width, int height) {
		int[] indices = new int[width * height * 4];
		int counter = 0;
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < height; x++) {
				indices[counter] 	 = height*y + x;
				indices[counter + 1] = height*y + x + 1;
				indices[counter + 2] = height*(y + 1) + x + 1;
				indices[counter + 3] = height*(y + 1) + x;
				counter += 4;
			}
		}
		return indices;
	}
	
	private static double[] generateVertexBuffer(MapTile[][] tileMap) {
		int width = tileMap.length;
		int height = tileMap[0].length;
		//(3 xyz coords + 2 texture coords) * 4 vertices per quad * map width * map height
		double[] vertices = new double[(3 + 2) * width * height];
		boolean[][] vertexHeights = MapWallParser.createWallHeightMap(tileMap);
		//WallType[][] wallTypeMap = WallTypeMapParser.createWallTypeMap(vertexHeights, width, height);
		//Orientation[][] wallOrientations = MapWallOrientationParser.generateOrientationMap(vertexHeights, wallTypeMap);
		return vertices;
	}

}
