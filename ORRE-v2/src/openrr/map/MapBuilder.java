package openrr.map;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

import openrr.map.soil.SoilType;
import orre.geom.Dimension2D;
import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.gl.materials.Material;
import orre.resources.loaders.map.MapTexturePack;
import orre.resources.loaders.obj.GeometryBufferGenerator;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class MapBuilder {
	private static final int verticesPerTile = 4;
	private static final int doublesPerVertex = 3 + 2 + 0; //xyz coordinate + uv texture coordinate + xyz normal coordinate
	private static final double[] vertex = new double[doublesPerVertex];

	public static SceneNode buildMapGeometry(MapTile[][] tileMap, MapTexturePack texturePack) {
		int mapWidth = tileMap.length;
		int mapHeight = tileMap[0].length;
		Dimension2D mapSize = new Dimension2D(mapWidth, mapHeight);
		//wallmap size: mapWidth + 1, mapHeight + 1
		boolean[][] wallMap = IsWallMapBuilder.buildIsWallMap(tileMap, mapSize);
		//wallTypeMap size: mapWidth, mapHeight
		WallType[][] wallTypeMap = WallTypeMapBuilder.buildWallTypeMap(wallMap, mapSize);
		//orientationMap size: mapWidth, mapHeight
		Orientation[][] orientationMap = MapWallOrientationBuilder.buildOrientationMap(wallMap, wallTypeMap, mapSize);
		
		SceneNode mapRootNode = generateMapSceneNode(wallTypeMap, orientationMap, tileMap, mapSize, texturePack);
		return mapRootNode;
	}

	private static SceneNode generateMapSceneNode(WallType[][] wallTypeMap, Orientation[][] orientationMap, MapTile[][] tileMap, Dimension2D mapSize, MapTexturePack texturePack) {
		SceneNode rootNode = new EmptySceneNode();
		
		//bind a first default texture
		texturePack.bindTexture(SoilType.DIRT, WallType.ground);
		
		int numVertices = (mapSize.width + 1) * (mapSize.height + 1);
		int geometryBufferSize = numVertices * verticesPerTile * doublesPerVertex;
		
		DoubleBuffer geometryDataBuffer = BufferUtils.createDoubleBuffer(geometryBufferSize);
		
//		double[] normal = new double[3];
		
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
					texturePack.bindTexture(tileSoilType, tileWallType);
				}
				double[] textureCoordinates = texturePack.getTextureCoordinates(orientation);
				
				putVertex(geometryDataBuffer, x, y, mapTile.tileHeight[0][0], textureCoordinates[0], textureCoordinates[1]);
				putVertex(geometryDataBuffer, x, y, mapTile.tileHeight[1][0], textureCoordinates[2], textureCoordinates[1]);
				putVertex(geometryDataBuffer, x, y, mapTile.tileHeight[1][1], textureCoordinates[2], textureCoordinates[3]);
				putVertex(geometryDataBuffer, x, y, mapTile.tileHeight[0][1], textureCoordinates[0], textureCoordinates[3]);
			}
		}
		
		compileGeometryBuffer(texturePack, rootNode, geometryDataBuffer);
		return rootNode;
	}

	private static void putVertex(DoubleBuffer geometryDataBuffer, int x, int y, int z, double u, double v) {
		vertex[0] = x;
		vertex[1] = y;
		vertex[2] = z;
		vertex[3] = u;
		vertex[4] = v;
		geometryDataBuffer.put(vertex);
	}

	private static void compileGeometryBuffer(MapTexturePack texturePack, SceneNode rootNode, DoubleBuffer geometryDataBuffer) {
		Material currentMaterial = texturePack.generateBoundTextureMaterial();
		int[] indices = generateIndexBuffer(geometryDataBuffer.position());
		double[] geometryData = new double[geometryDataBuffer.position()];
		geometryDataBuffer.rewind();
		geometryDataBuffer.get(geometryData);
		GeometryBuffer buffer = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES_AND_TEXTURES, geometryData, indices);
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