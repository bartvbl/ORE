package openrr.map;

import java.util.Arrays;

import orre.geom.Vector3D;
import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.loaders.obj.GeometryBufferGenerator;

public class MapBuilder {

	public static GeometryBuffer buildMapGeometry(MapTile[][] tileMap) {
		int width = tileMap.length;
		int height = tileMap[0].length;
		int[] indices = generateIndexBuffer(width, height);
		double[] vertices = generateVertexBuffer(tileMap); 
		GeometryBuffer buffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_AND_NORMALS, vertices, indices);
		return buffer;
	}

	private static int[] generateIndexBuffer(int width, int height) {
		int[] indices = new int[6 * width * height];
		for(int counter = 0; counter < 6 * width * height; counter++) {
			indices[counter] = counter;
		}
		return indices;
	}
	
	private static double[] generateVertexBuffer(MapTile[][] tileMap) {
		int width = tileMap.length;
		int height = tileMap[0].length;
		//(3 xyz coords + 2 texture coords) * (map width + 1 vertex for the final tile) * (map height + 1 vertex for the final tile)
		double[] vertices = new double[(6*3 + 6*3) * (width) * (height)];
		int counter = 0;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int[][] tileHeight = tileMap[x][y].tileHeight;
				boolean[][] vertexHeights = MapWallParser.exploreNeighbourhood(tileMap, x, y);
				//WallType wallTypeMap = WallTypeMapParser.parseWallType(vertexHeights);
				int wallHeight = 2;
				double mapHeightDownScaleFactor = 3;
				tileHeight[0][0] += vertexHeights[0][0] ? wallHeight:0;
				tileHeight[1][0] += vertexHeights[1][0] ? wallHeight:0;
				tileHeight[1][1] += vertexHeights[1][1] ? wallHeight:0;
				tileHeight[0][1] += vertexHeights[0][1] ? wallHeight:0;
				
				Vector3D vec1 = new Vector3D(1, 0, (((double)tileHeight[1][0] / mapHeightDownScaleFactor) - (double)tileHeight[0][0] / mapHeightDownScaleFactor));
				Vector3D vec2 = new Vector3D(0, 1, (((double)tileHeight[0][1] / mapHeightDownScaleFactor) - (double)tileHeight[0][0] / mapHeightDownScaleFactor));
				Vector3D normal = vec1.vectorProduct(vec2).normalize();
				
				pasteVertexAt(vertices, counter, x, y, ((double)tileHeight[0][0] / mapHeightDownScaleFactor)); 
				counter += 3;
//				pasteTexCoordAt(vertices, counter, 0, 0);
//				counter += 2;
				pasteVertexAt(vertices, counter, normal.x, normal.y, normal.z); 
				counter += 3;
				
				pasteVertexAt(vertices, counter, x + 1, y, ((double)tileHeight[1][0] / mapHeightDownScaleFactor)); 
				counter += 3;
//				pasteTexCoordAt(vertices, counter, 1, 0);
//				counter += 2;
				pasteVertexAt(vertices, counter, normal.x, normal.y, normal.z); 
				counter += 3;

				pasteVertexAt(vertices, counter, x, y + 1, ((double)tileHeight[0][1] / mapHeightDownScaleFactor)); 
				counter += 3;
//				pasteTexCoordAt(vertices, counter, 0, 1);
//				counter += 2;
				pasteVertexAt(vertices, counter, normal.x, normal.y, normal.z); 
				counter += 3;
//				
				pasteVertexAt(vertices, counter, x + 1, y, ((double)tileHeight[1][0] / mapHeightDownScaleFactor)); 
				counter += 3;
//				pasteTexCoordAt(vertices, counter, 0, 0);
//				counter += 2;
				pasteVertexAt(vertices, counter, normal.x, normal.y, normal.z); 
				counter += 3;
				
				pasteVertexAt(vertices, counter, x + 1, y + 1, ((double)tileHeight[1][1] / mapHeightDownScaleFactor)); 
				counter += 3;
//				pasteTexCoordAt(vertices, counter, 1, 0);
//				counter += 2;
				pasteVertexAt(vertices, counter, normal.x, normal.y, normal.z); 
				counter += 3;
				
				pasteVertexAt(vertices, counter, x, y + 1, ((double)tileHeight[0][1] / mapHeightDownScaleFactor)); 
				counter += 3;
//				pasteTexCoordAt(vertices, counter, 1, 1);
//				counter += 2;
				pasteVertexAt(vertices, counter, normal.x, normal.y, normal.z); 
				counter += 3;
			}
		}
		return vertices;
	}
	
	private static void drawTriangle(Vector3D positionVector, Vector3D edge1, Vector3D edge2) {
		
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
