package openrr.map;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

import openrr.map.loader.MapTexturePack;
import openrr.map.loader.SubTextureCoordinate;
import openrr.map.soil.SoilType;
import orre.geom.Dimension2D;
import orre.geom.Vector3D;
import orre.geom.Vertex3D;
import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryNode;
import orre.geom.vbo.GeometryBufferGenerator;
import orre.gl.materials.Material;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class MapBuilder {
	private static final int verticesPerTile = 6;
	private static final int doublesPerVertex = 3 + 2 + 3; //xyz coordinate + uv texture coordinate + xyz normal coordinate
	private static final double wallHeight = 1.1;
	private static final double tileSide = 1;

	public static SceneNode buildMapGeometry(MapTile[][] tileMap, MapTexturePack texturePack) {
		System.out.println("building map..");
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		Dimension2D mapSize = new Dimension2D(mapWidth, mapHeight);
		//wallmap size: mapWidth + 1, mapHeight + 1
		boolean[][] wallMap = IsWallMapBuilder.buildIsWallMap(tileMap, mapSize);
		//wallTypeMap size: mapWidth, mapHeight
		WallType[][] wallTypeMap = WallTypeMapBuilder.buildWallTypeMap(wallMap, mapSize);
		//orientationMap size: mapWidth, mapHeight
		Orientation[][] orientationMap = MapWallOrientationBuilder.buildOrientationMap(wallMap, wallTypeMap, mapSize);
		
		SceneNode mapRootNode = generateMapSceneNode(wallMap, wallTypeMap, orientationMap, tileMap, mapSize, texturePack);
		System.out.println("build complete.");
		return mapRootNode;
	}

	private static SceneNode generateMapSceneNode(boolean[][] wallMap, WallType[][] wallTypeMap, Orientation[][] orientationMap, MapTile[][] tileMap, Dimension2D mapSize, MapTexturePack texturePack) {
		SceneNode rootNode = new ContainerNode();
		
		//bind a first default texture
		texturePack.bindTexture(SoilType.DIRT, WallType.ground);
		
		int numVertices = (mapSize.width) * (mapSize.height);
		int geometryBufferSize = numVertices * verticesPerTile * doublesPerVertex;
		
		DoubleBuffer geometryDataBuffer = BufferUtils.createDoubleBuffer(geometryBufferSize);
		
		Vector3D[][] mapVertices = calculateMapVertices(wallMap, tileMap, mapSize);
		//0 index is for the triangle that intersects with 0, 0.5. 1 for the other.
		Vector3D[][][] mapNormals = new Vector3D[mapSize.width][mapSize.height][2];
		
		for(int x = 0; x < mapSize.width; x++) {
			for(int y = 0; y < mapSize.height; y++) {
				WallType tileWallType = wallTypeMap[x][y];
				Orientation orientation = orientationMap[x][y];
				boolean rotated = isRotated(orientation);
				
				calculateTileNormals(x, y, mapVertices, mapNormals[x][y], rotated);
			}
		}

		//getNormalAtVertex(x, y, mapNormals, orientation);
		
		for(int x = 0; x < mapSize.width; x++) {
			for(int y = 0; y < mapSize.height; y++) {
				MapTile mapTile = tileMap[x][y];
				SoilType tileSoilType = mapTile.getSoilType();
				WallType tileWallType = wallTypeMap[x][y];
				Orientation orientation = orientationMap[x][y];
				
				if(!texturePack.isBound(tileSoilType, tileWallType)) {
					if(!(geometryDataBuffer.position() == 0)) {
						compileGeometryBuffer(texturePack, rootNode, geometryDataBuffer);
					}
				}
				texturePack.bindTexture(tileSoilType, tileWallType);
				
				SubTextureCoordinate textureCoordinate = texturePack.getTextureCoordinates();
				Vertex3D[] vertices = MapCoordinateRotator.generateRotatedTileCoordinates(x, y, mapVertices, mapNormals, textureCoordinate, orientation);
				
				putVertices(vertices, geometryDataBuffer);
			}
		}
		
		compileGeometryBuffer(texturePack, rootNode, geometryDataBuffer);
		return rootNode;
	}

	private static void calculateTileNormals(int x, int y, Vector3D[][] mapVertices, Vector3D[] normals, boolean rotated) {
		if(!rotated) {
			//tile origin is at bottom left corner, hypothenuse is antidiagonal
			Vector3D triangle1edge1 = mapVertices[x + 1][y + 1].minus(mapVertices[x][y]);
			Vector3D triangle1edge2 = mapVertices[x][y + 1].minus(mapVertices[x][y]);
			normals[0] = triangle1edge1.vectorProduct(triangle1edge2);
			
			Vector3D triangle2edge1 = mapVertices[x + 1][y + 1].minus(mapVertices[x][y]);
			Vector3D triangle2edge2 = mapVertices[x + 1][y].minus(mapVertices[x][y]);
			normals[1] = triangle2edge1.vectorProduct(triangle2edge2);
		} else {
			//tile origin is bottom right corner, hyopthenuse is leading diagonal
			Vector3D triangle1edge1 = mapVertices[x + 1][y + 1].minus(mapVertices[x + 1][y]);
			Vector3D triangle1edge2 = mapVertices[x][y + 1].minus(mapVertices[x + 1][y]);
			normals[0] = triangle1edge1.vectorProduct(triangle1edge2);
			
			Vector3D triangle2edge1 = mapVertices[x][y + 1].minus(mapVertices[x + 1][y]);
			Vector3D triangle2edge2 = mapVertices[x][y].minus(mapVertices[x + 1][y]);
			normals[1] = triangle2edge1.vectorProduct(triangle2edge2);
		}
	}

	private static boolean isRotated(Orientation orientation) {
		if(orientation == Orientation.east || orientation == Orientation.west) {
			return true;
		} else {
			return false;
		}
	}

	private static Vector3D[][] calculateMapVertices(boolean[][] wallMap, MapTile[][] tileMap, Dimension2D mapSize) {
		Vector3D[][] mapVertices = new Vector3D[mapSize.width + 1][mapSize.height + 1];
		for(int x = 0; x < mapSize.width + 1; x++) {
			for(int y = 0; y < mapSize.height + 1; y++) {
				int xCoord = x;
				int yCoord = y;
				
				if(xCoord == mapSize.width)  xCoord -= 1;
				if(yCoord == mapSize.height) yCoord -= 1;
				
				MapTile mapTile = tileMap[xCoord][yCoord];
				double tileHeight = calculateTileEdgeHeight(wallMap, xCoord, yCoord, mapTile);
				mapVertices[x][y] = new Vector3D(((double)x * tileSide) - (tileSide / 2d), ((double)y * tileSide) - (tileSide / 2d), tileHeight);
			}
		}
		return mapVertices;
	}
	
	private static void putVertices(Vertex3D[] vertices, DoubleBuffer geometryDataBuffer) {
		for(Vertex3D vertex : vertices) {
			double[] vertexData = vertex.toArray();
			geometryDataBuffer.put(vertexData);
		}
	}

	private static double calculateTileEdgeHeight(boolean[][] wallMap, int x, int y, MapTile mapTile) {
		double vertexWallHeight = wallMap[x][y] ? wallHeight : 0;
		return mapTile.tileHeight[0][0] + vertexWallHeight;
	}

	private static void compileGeometryBuffer(MapTexturePack texturePack, SceneNode rootNode, DoubleBuffer geometryDataBuffer) {
		Material currentMaterial = texturePack.generateBoundTextureMaterial();
		IntBuffer indices = generateIndexBuffer(geometryDataBuffer.position());
		GeometryNode buffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryDataBuffer, indices);
		GeometryNode normals = GeometryBufferGenerator.generateNormalsGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryDataBuffer, indices);
		currentMaterial.addChild(buffer);
		currentMaterial.addChild(normals);
		rootNode.addChild(currentMaterial);
	}

	private static IntBuffer generateIndexBuffer(int numVertices) {
		IntBuffer indices = BufferUtils.createIntBuffer(numVertices);
		for(int i = 0; i < numVertices; i++) {
			indices.put(i);
		}
		return indices;
	}
	
}