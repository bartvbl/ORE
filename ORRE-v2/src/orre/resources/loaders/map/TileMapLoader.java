package orre.resources.loaders.map;

import java.util.zip.ZipFile;

import openrr.map.MapTile;
import openrr.map.Soil;
import nu.xom.Attribute;
import nu.xom.Element;

public class TileMapLoader {

	public static MapTile[][] loadTileMap(Element mapDefinitionElement, ZipFile mapFile) {
		int[] mapSize = parseMapSize(mapDefinitionElement);
		int width = mapSize[0];
		int height = mapSize[1];
		boolean[][] wallMap = WallMapLoader.loadWallMap(mapFile, mapDefinitionElement, width, height);
		Soil[][] soilMap = SoilMapLoader.loadSoilMap(mapDefinitionElement, width, height);
		double[][] heightMap = HeightMapLoader.loadHeightMap(mapFile, mapDefinitionElement, width, height);
		return createTileMap(wallMap, soilMap, heightMap, width, height);
	}

	private static MapTile[][] createTileMap(boolean[][] wallMap, Soil[][] soilMap, double[][] heightMap, int width, int height) {
		MapTile[][] tileMap = new MapTile[width][height];
		return tileMap;
	}

	private static int[] parseMapSize(Element mapDefinitionElement) {
		Element mapDimension = mapDefinitionElement.getFirstChildElement("mapDimension");
		int width = Integer.parseInt(mapDimension.getAttributeValue("width"));
		int height = Integer.parseInt(mapDimension.getAttributeValue("height"));
		return new int[]{width, height};
	}

}
