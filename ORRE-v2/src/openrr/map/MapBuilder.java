package openrr.map;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

import openrr.map.soil.SoilType;
import orre.geom.Dimension2D;
import orre.geom.Vertex3D;
import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.gl.materials.Material;
import orre.resources.loaders.map.MapTexturePack;
import orre.resources.loaders.map.SubTextureCoordinate;
import orre.resources.loaders.obj.GeometryBufferGenerator;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class MapBuilder {
	private static final int verticesPerTile = 6;
	private static final int doublesPerVertex = 3 + 2 + 0; //xyz coordinate + uv texture coordinate + xyz normal coordinate
	private static final double wallHeight = 1.35;
	private static final double tileHeightMultiplyer = 0.4;

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
		SceneNode rootNode = new EmptySceneNode();
		
		//bind a first default texture
		texturePack.bindTexture(SoilType.DIRT, WallType.ground);
		
		int numVertices = (mapSize.width + 1) * (mapSize.height + 1);
		int geometryBufferSize = numVertices * verticesPerTile * doublesPerVertex;
		
		DoubleBuffer geometryDataBuffer = BufferUtils.createDoubleBuffer(geometryBufferSize);
		
//		double[] normal = new double[3];
		double[][] tileHeight = new double[2][2];
		
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
				
				calculateTileEdgeHeight(wallMap, tileHeight, x, y, mapTile);
				
				SubTextureCoordinate textureCoordinate = texturePack.getTextureCoordinates();
				Vertex3D[] vertices = MapCoordinateRotator.generateRotatedTileCoordinates(x, y, tileHeight, textureCoordinate, orientation);
				
				putVertices(geometryDataBuffer, vertices);
			}
		}
		
		compileGeometryBuffer(texturePack, rootNode, geometryDataBuffer);
		return rootNode;
	}

	private static void putVertices(DoubleBuffer geometryDataBuffer, Vertex3D[] vertices) {
		for(Vertex3D vertex : vertices) {
			geometryDataBuffer.put(vertex.toArray());
		}
	}

	private static void calculateTileEdgeHeight(boolean[][] wallMap, double[][] tileHeight, int x, int y, MapTile mapTile) {
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				double vertexWallHeight = wallMap[x + i][y + j] ? wallHeight : 0;
				tileHeight[i][j] = mapTile.tileHeight[i][j] * tileHeightMultiplyer + vertexWallHeight;
			}
		}
	}

	private static void compileGeometryBuffer(MapTexturePack texturePack, SceneNode rootNode, DoubleBuffer geometryDataBuffer) {
		Material currentMaterial = texturePack.generateBoundTextureMaterial();
		int[] indices = generateIndexBuffer(geometryDataBuffer.position());
		double[] geometryData = new double[geometryDataBuffer.position()];
		geometryDataBuffer.rewind();
		geometryDataBuffer.get(geometryData);
		GeometryBuffer buffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_TEXTURES_NORMALS, geometryData, indices);
		geometryDataBuffer.clear();
		currentMaterial.addChild(buffer);
		rootNode.addChild(currentMaterial);
	}

	private static int[] generateIndexBuffer(int numVertices) {
		int[] indices = new int[numVertices];
		for(int i = 0; i < numVertices; i++) {
			indices[i] = i;
		}
		return indices;
	}
	
}