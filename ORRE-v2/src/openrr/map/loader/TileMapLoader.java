package openrr.map.loader;

import java.io.IOException;

import orre.geom.Dimension2D;
import openrr.map.MapTile;
import openrr.map.soil.SoilType;
import nu.xom.Element;

public class TileMapLoader {
	public static MapTile[][] loadTileMap(MapLoadingContext context) throws IOException {
		Element mapDefinitionElement = context.mapXMLRootElement.getFirstChildElement("mapDefinition");
		
		context.mapSize = parseMapSize(mapDefinitionElement);
		context.wallMap = loadWallMap(context, mapDefinitionElement);
		context.soilMap = loadSoilMap(context, mapDefinitionElement);
		context.heightMap = loadHeightMap(context, mapDefinitionElement);
		
		return createTileMap(context);
	}
	
	private static Dimension2D parseMapSize(Element mapDefinitionElement) {
		Element mapDimension = mapDefinitionElement.getFirstChildElement("mapDimension");
		int width = Integer.parseInt(mapDimension.getAttributeValue("width"));
		int height = Integer.parseInt(mapDimension.getAttributeValue("height"));
		return new Dimension2D(width, height);
	}

	private static boolean[][] loadWallMap(MapLoadingContext context,
			Element mapDefinitionElement) throws IOException {
		Element wallMapElement = mapDefinitionElement.getFirstChildElement("wallMap");
		boolean[][] wallMap = WallMapLoader.loadWallMap(wallMapElement, context);
		return wallMap;
	}

	private static SoilType[][] loadSoilMap(MapLoadingContext context,
			Element mapDefinitionElement) throws IOException {
		Element soilMapElement = mapDefinitionElement.getFirstChildElement("soilMap");
		SoilType[][] soilMap = SoilMapLoader.loadSoilMap(soilMapElement, context);
		return soilMap;
	}

	private static double[][] loadHeightMap(MapLoadingContext context,
			Element mapDefinitionElement) throws IOException {
		Element heightMapElement = mapDefinitionElement.getFirstChildElement("heightMap");
		double[][] heightMap = HeightMapLoader.loadHeightMap(heightMapElement, context);
		return heightMap;
	}

	private static MapTile[][] createTileMap(MapLoadingContext context) {
		Dimension2D mapSize = context.mapSize;
		MapTile[][] tileMap = new MapTile[mapSize.width][mapSize.height];
		for(int x = 0; x < mapSize.width; x++) {
			for(int y = 0; y < mapSize.height; y++) {
				double[][] tileHeight = new double[2][2];
				tileHeight[0][0] = context.heightMap[x][y];
				tileHeight[0][1] = context.heightMap[x][y + 1];
				tileHeight[1][0] = context.heightMap[x + 1][y];
				tileHeight[1][1] = context.heightMap[x + 1][y + 1];
				
				tileMap[x][y] = new MapTile(context.wallMap[x][y], context.soilMap[x][y], tileHeight);
			}
		}
		return tileMap;
	}
}
