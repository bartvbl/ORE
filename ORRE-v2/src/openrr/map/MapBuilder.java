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
	private static final int verticesPerTile = 6;
	private static final int doublesPerVertex = 3 + 2 + 0; //xyz coordinate + uv texture coordinate + xyz normal coordinate
	private static final double[] vertex = new double[doublesPerVertex];
	private static final double wallHeight = 1.5;

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
		
		SceneNode mapRootNode = generateMapSceneNode(wallMap, wallTypeMap, orientationMap, tileMap, mapSize, texturePack);
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
				
				for(int i = 0; i < 1; i++) {
					for(int j = 0; j < 1; j++) {						
						tileHeight[i][j] = mapTile.tileHeight[i][j] + (wallMap[x + i][y + j] ? wallHeight : 0);
					}
				}
				
				
				double[][] coordinates6x3 = MapCoordinateRotator.generateRotatedTileCoordinates(x, y, tileHeight, orientation);
				double[][] textureCoordinates2x2 = texturePack.getTextureCoordinates(orientation);
				
				putVertex(geometryDataBuffer, coordinates6x3[0], textureCoordinates2x2[0][0], textureCoordinates2x2[0][1]);
				putVertex(geometryDataBuffer, coordinates6x3[1], textureCoordinates2x2[1][0], textureCoordinates2x2[0][1]);
				putVertex(geometryDataBuffer, coordinates6x3[2], textureCoordinates2x2[1][0], textureCoordinates2x2[1][1]);
				
				putVertex(geometryDataBuffer, coordinates6x3[3], textureCoordinates2x2[0][0], textureCoordinates2x2[0][1]);
				putVertex(geometryDataBuffer, coordinates6x3[4], textureCoordinates2x2[1][0], textureCoordinates2x2[1][1]);
				putVertex(geometryDataBuffer, coordinates6x3[5], textureCoordinates2x2[0][0], textureCoordinates2x2[1][1]);
			}
		}
		
		compileGeometryBuffer(texturePack, rootNode, geometryDataBuffer);
		return rootNode;
	}

	private static void putVertex(DoubleBuffer geometryDataBuffer, double[] coordinates6x3, double u, double v) {
		vertex[0] = coordinates6x3[0];
		vertex[1] = coordinates6x3[1];
		vertex[2] = coordinates6x3[2];
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