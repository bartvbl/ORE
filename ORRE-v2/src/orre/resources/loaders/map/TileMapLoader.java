package orre.resources.loaders.map;

import java.util.zip.ZipFile;

import orre.resources.ResourceCache;

import openrr.map.MapTile;
import openrr.map.soil.Soil;
import nu.xom.Attribute;
import nu.xom.Element;

public class TileMapLoader {

	public static MapTile[][] loadTileMap(Element mapDefinitionElement, ZipFile mapFile) {
		int[] mapSize = parseMapSize(mapDefinitionElement);
		int width = mapSize[0];
		int height = mapSize[1];
		boolean[][] wallMap = WallMapLoader.loadWallMap(mapFile, mapDefinitionElement, width, height);
		Soil[][] soilMap = SoilMapLoader.loadSoilMap(mapDefinitionElement, width, height);
		int[][] heightMap = HeightMapLoader.loadHeightMap(mapFile, mapDefinitionElement, width, height);
		return createTileMap(wallMap, soilMap, heightMap, width, height);
	}

	private static MapTile[][] createTileMap(boolean[][] wallMap, Soil[][] soilMap, int[][] heightMap, int width, int height) {
		MapTile[][] tileMap = new MapTile[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int[][] tileHeight = new int[2][2];
				tileHeight[0][0] = heightMap[x][y];
				tileHeight[0][1] = heightMap[x][y + 1];
				tileHeight[1][0] = heightMap[x + 1][y];
				tileHeight[1][1] = heightMap[x + 1][y + 1];
				
				tileMap[x][y] = new MapTile(wallMap[x][y], soilMap[x][y], tileHeight);
			}
		}
		return tileMap;
	}

	private static int[] parseMapSize(Element mapDefinitionElement) {
		Element mapDimension = mapDefinitionElement.getFirstChildElement("mapDimension");
		int width = Integer.parseInt(mapDimension.getAttributeValue("width"));
		int height = Integer.parseInt(mapDimension.getAttributeValue("height"));
		return new int[]{width, height};
	}

}
