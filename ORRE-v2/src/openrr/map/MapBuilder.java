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
		System.out.println("number of vertices: " + ((3*6 + 6*2) * (width + 1) * (height + 1)));
		//(6 xyz coords + 2 texture coords) * (map width + 1 vertex for the final tile) * (map height + 1 vertex for the final tile)
		double[] vertices = new double[(3*6 + 6*2) * (width + 1) * (height + 1)];
		int counter = 0;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int[][] tileHeight = tileMap[x][y].tileHeight;
				boolean[][] vertexHeights = MapWallParser.exploreNeighbourhood(tileMap, x, y);
				//WallType wallTypeMap = WallTypeMapParser.parseWallType(vertexHeights);
				
				pasteVertexAt(vertices, counter, x, y, tileHeight[0][0]); 
				counter += 3;
				pasteTexCoordAt(vertices, counter, 0, 0);
				counter += 2;
				pasteVertexAt(vertices, counter, x, y + 1, tileHeight[0][1]); 
				counter += 3;
				pasteTexCoordAt(vertices, counter, 0, 1);
				counter += 2;
				pasteVertexAt(vertices, counter, x + 1, y, tileHeight[1][0]); 
				counter += 3;
				pasteTexCoordAt(vertices, counter, 1, 0);
				counter += 2;
				
				pasteVertexAt(vertices, counter, x, y, tileHeight[0][0]); 
				counter += 3;
				pasteTexCoordAt(vertices, counter, 0, 0);
				counter += 2;
				pasteVertexAt(vertices, counter, x + 1, y, tileHeight[1][0]); 
				counter += 3;
				pasteTexCoordAt(vertices, counter, 1, 0);
				counter += 2;
				pasteVertexAt(vertices, counter, x + 1, y + 1, tileHeight[1][1]); 
				counter += 3;
				pasteTexCoordAt(vertices, counter, 1, 1);
				counter += 2;
			}
		}
		return vertices;
	}
	
	private static void pasteVertexAt(double[] vertices, int startIndex, double x, double y, double z) {
		vertices[startIndex + 0] = x;
		vertices[startIndex + 1] = y;
		vertices[startIndex + 2] = z;
	}
	
	private static void pasteTexCoordAt(double[] vertices, int startIndex, double u, double v) {
		vertices[startIndex + 0] = u;
		vertices[startIndex + 1] = v;
	}

}
