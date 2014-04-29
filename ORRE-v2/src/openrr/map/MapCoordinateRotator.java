package openrr.map;

import orre.geom.Vector3D;
import orre.geom.Vertex3D;
import orre.resources.loaders.map.SubTextureCoordinate;
import orre.util.ArrayUtils;

public class MapCoordinateRotator {
	private static Vertex3D[] vertices = new Vertex3D[6];
	private static double tileSide = 1;
	
	public static Vertex3D[] generateRotatedTileCoordinates(int x, int y, double[][] tileHeight, SubTextureCoordinate textureCoordinate, Orientation orientation) {
		Vector3D tileOrigin = generateTileOrigin(x + tileSide / 2, y + tileSide / 2, tileHeight, orientation);
		Vector3D[] cornerVertices = generateCornerVertices(tileOrigin, tileHeight);
		rotateCornerVertices(cornerVertices, orientation);
		Vector3D[] normals = calculateNormals(cornerVertices);
		createVertices(cornerVertices, textureCoordinate, normals);
		return vertices;
	}

	private static void createVertices(Vector3D[] cornerVertices, SubTextureCoordinate rotatedTextureCoordinates, Vector3D[] normals) {
		
		vertices[0] = createVertex(cornerVertices[0], rotatedTextureCoordinates.u1, rotatedTextureCoordinates.v1, normals[0]);
		vertices[1] = createVertex(cornerVertices[1], rotatedTextureCoordinates.u2, rotatedTextureCoordinates.v1, normals[0]);
		vertices[2] = createVertex(cornerVertices[3], rotatedTextureCoordinates.u1, rotatedTextureCoordinates.v2, normals[0]);
		
		vertices[3] = createVertex(cornerVertices[1], rotatedTextureCoordinates.u2, rotatedTextureCoordinates.v1, normals[1]);
		vertices[4] = createVertex(cornerVertices[2], rotatedTextureCoordinates.u2, rotatedTextureCoordinates.v2, normals[1]);
		vertices[5] = createVertex(cornerVertices[3], rotatedTextureCoordinates.u1, rotatedTextureCoordinates.v2, normals[1]);
	}

	private static Vertex3D createVertex(Vector3D coordinate, double textureU, double textureV, Vector3D normal) {
		return new Vertex3D(coordinate.x, coordinate.y, coordinate.z, textureU, textureV, normal.x, normal.y, normal.z);
	}

	private static Vector3D[] calculateNormals(Vector3D[] cornerVertices) {
		Vector3D normal1Edge1 = cornerVertices[3].minus(cornerVertices[0]);
		Vector3D normal1Edge2 = cornerVertices[1].minus(cornerVertices[0]);
		
		Vector3D normal2Edge1 = cornerVertices[3].minus(cornerVertices[2]);
		Vector3D normal2Edge2 = cornerVertices[1].minus(cornerVertices[2]);
		
		Vector3D[] normals = new Vector3D[2];
		normals[0] = normal1Edge2.vectorProduct(normal1Edge1).normalize();
		normals[1] = normal2Edge1.vectorProduct(normal2Edge2).normalize();
		return normals;
	}

	private static Vector3D[] generateCornerVertices(Vector3D tileOrigin, double[][] tileHeight) {
		Vector3D[] cornerVertices = new Vector3D[4];
		cornerVertices[0] = new Vector3D(tileOrigin.x - tileSide / 2, tileOrigin.y - tileSide / 2, tileHeight[0][0]);
		cornerVertices[1] = new Vector3D(tileOrigin.x + tileSide / 2, tileOrigin.y - tileSide / 2, tileHeight[1][0]);
		cornerVertices[2] = new Vector3D(tileOrigin.x + tileSide / 2, tileOrigin.y + tileSide / 2, tileHeight[1][1]);
		cornerVertices[3] = new Vector3D(tileOrigin.x - tileSide / 2, tileOrigin.y + tileSide / 2, tileHeight[0][1]);
		return cornerVertices;
	}

	private static void rotateCornerVertices(Vector3D[] cornerVertices, Orientation orientation) {
		switch(orientation) {
			default:
			case north: 
				return; //leave vertex order intact
			case west:
				ArrayUtils.shiftLeft(cornerVertices, 1);
				return;
			case south:
				ArrayUtils.shiftLeft(cornerVertices, 2);
				return;
			case east: 
				ArrayUtils.shiftLeft(cornerVertices, 3);
				return;
		}
	}
	
	private static Vector3D generateTileOrigin(double x, double y, double[][] tileHeight, Orientation orientation) {
		switch(orientation) {
			default:
			case north: return new Vector3D(x, y, tileHeight[0][0]);
			case east: return new Vector3D(x, y, tileHeight[1][0]);
			case south: return new Vector3D(x, y, tileHeight[1][1]);
			case west: return new Vector3D(x, y, tileHeight[0][1]);
		}
	}
}
