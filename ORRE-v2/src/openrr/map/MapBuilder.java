package openrr.map;

import java.util.Arrays;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.loaders.obj.GeometryBufferGenerator;

public class MapBuilder {

	public static GeometryBuffer buildMapGeometry(MapTile[][] tileMap) {
		int width = tileMap.length;
		int height = tileMap[0].length;
		int[] indices = generateIndexBuffer(width, height);
		double[] vertices = generateVertexBuffer(tileMap); 
		GeometryBuffer buffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_AND_TEXTURES, vertices, indices);
		return buffer;
	}

	private static int[] generateIndexBuffer(int width, int height) {
		int[] indices = new int[width * height * 6];
		int counter = 0;
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < height; x++) {
				//indices to draw two triangles.
				indices[counter] 	 = height*y + x;
				indices[counter + 1] = height*y + x + 1;
				indices[counter + 2] = height*(y + 1) + x + 1;
				
				indices[counter + 3] = height*y + x + 1;
				indices[counter + 4] = height*(y + 1) + x + 1;
				indices[counter + 5] = height*(y + 1) + x;
				counter += 6;
			}
		}
		return indices;
	}
	
	private static double[] generateVertexBuffer(MapTile[][] tileMap) {
		int width = tileMap.length;
		int height = tileMap[0].length;
		//(3 xyz coords + 2 texture coords) * (map width + 1 vertex for the final tile) * (map height + 1 vertex for the final tile)
		double[] vertices = new double[(3 + 2) * (width + 1) * (height + 1)];
		boolean[][] vertexHeights = MapWallParser.createWallHeightMap(tileMap);
		//WallType[][] wallTypeMap = WallTypeMapParser.createWallTypeMap(vertexHeights, width, height);
		//Orientation[][] wallOrientations = MapWallOrientationParser.generateOrientationMap(vertexHeights, wallTypeMap);
		return vertices;
	}

}
