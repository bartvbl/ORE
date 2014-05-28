package openrr.map;

import openrr.map.loader.SubTextureCoordinate;
import orre.geom.Vector3D;
import orre.geom.Vertex3D;
import orre.util.ArrayUtils;

public class MapCoordinateRotator {
	private static Vertex3D[] vertices = new Vertex3D[6];
	
	public static Vertex3D[] generateRotatedTileCoordinates(int x, int y, Vector3D[][] mapVertices, Vector3D[][][] mapNormals, SubTextureCoordinate textureCoordinate, Orientation orientation) {
		Vector3D[] cornerVertices = generateCornerVertices(x, y, mapVertices);
		Vector3D[] normals = calculateNormals(x, y, mapVertices, mapNormals);
		rotateCornerVertices(cornerVertices, orientation);
		//rotateCornerVertices(normals, orientation);
		createVertices(cornerVertices, textureCoordinate, normals);
		return vertices;
	}

	private static void createVertices(Vector3D[] cornerVertices, SubTextureCoordinate rotatedTextureCoordinates, Vector3D[] normals) {
		
		vertices[0] = createVertex(cornerVertices[0], rotatedTextureCoordinates.u1, rotatedTextureCoordinates.v1, normals[0]);
		vertices[1] = createVertex(cornerVertices[1], rotatedTextureCoordinates.u2, rotatedTextureCoordinates.v1, normals[0]);
		vertices[2] = createVertex(cornerVertices[3], rotatedTextureCoordinates.u1, rotatedTextureCoordinates.v2, normals[0]);
		
		vertices[3] = createVertex(cornerVertices[1], rotatedTextureCoordinates.u2, rotatedTextureCoordinates.v1, normals[0]);
		vertices[4] = createVertex(cornerVertices[2], rotatedTextureCoordinates.u2, rotatedTextureCoordinates.v2, normals[0]);
		vertices[5] = createVertex(cornerVertices[3], rotatedTextureCoordinates.u1, rotatedTextureCoordinates.v2, normals[0]);
	}

	private static Vertex3D createVertex(Vector3D coordinate, double textureU, double textureV, Vector3D normal) {
		return new Vertex3D(coordinate.x, coordinate.y, coordinate.z, textureU, textureV, normal.x, normal.y, normal.z);
	}

	private static Vector3D[] calculateNormals(int x, int y, Vector3D[][] mapVertices, Vector3D[][][] mapNormals) {
		Vector3D[] normals = new Vector3D[4];
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				normals[2*i + j] = calculateNormal(x + i, y + j, mapVertices, mapNormals);
			}
		}
		return normals;
	}

	private static Vector3D calculateNormal(int x, int y, Vector3D[][] mapVertices, Vector3D[][][] mapNormals) {
		Vector3D addedNormal = new Vector3D(0, 0, 0);
		for(int angle = 0; angle <= 360; angle += 90) {
			int offsetX1 = (int) Math.cos(Math.toRadians(angle));
			int offsetX2 = (int) Math.cos(Math.toRadians(angle - 90));
			
			int offsetY1 = (int) Math.sin(Math.toRadians(angle));
			int offsetY2 = (int) Math.sin(Math.toRadians(angle - 90));
			
			Vector3D vertex = getVertexAt(x, y, mapVertices);
			Vector3D vertex1 = getVertexAt(x + offsetX1, y + offsetY1, mapVertices);
			Vector3D vertex2 = getVertexAt(x + offsetX2, y + offsetY2, mapVertices);
			
			Vector3D edge1 = vertex.minus(vertex1);
			Vector3D edge2 = vertex.minus(vertex2);
			
			addedNormal = addedNormal.plus(edge2.vectorProduct(edge1));
		}
		
		return addedNormal.normalize();
	}

	private static Vector3D getVertexAt(int x, int y, Vector3D[][] mapVertices) {
		if(x < 0) x = 0;
		if(y < 0) y = 0;
		if(x >= mapVertices.length) x -= 1;
		if(y >= mapVertices[0].length) y -= 1;
		return mapVertices[x][y];
	}

	private static Vector3D[] generateCornerVertices(int x, int y, Vector3D[][] mapVertices) {
		Vector3D[] cornerVertices = new Vector3D[4];
		cornerVertices[0] = mapVertices[x][y];
		cornerVertices[1] = mapVertices[x+1][y];
		cornerVertices[2] = mapVertices[x+1][y+1];
		cornerVertices[3] = mapVertices[x][y+1];
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
}
