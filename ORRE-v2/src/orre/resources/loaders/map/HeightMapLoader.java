package orre.resources.loaders.map;

import java.awt.image.BufferedImage;
import java.util.zip.ZipFile;

import nu.xom.Element;
import nu.xom.Elements;

public class HeightMapLoader {

	//A tile has a reference point in the left bottom corner. 
	//So the corners of a tile at x, y have height values at x,y  x+1,y  x,y+1  x+1,y+1 in the heightMap matrix.
	
	//TODO: add behaviour for edges of height map
	
	private static final String zBottomLeft = "zBottomLeft";
	private static final String zBottomRight = "zBottomRight";
	private static final String zTopLeft = "zTopLeft";
	private static final String zTopRight = "zTopRight";
	
	public static int[][] loadHeightMap(ZipFile mapFile, Element mapDefinitionElement, int width, int height) {
		int[][] heightMap = new int[width + 1][height + 1];
		Element heightMapElement = mapDefinitionElement.getFirstChildElement("heightMap");
		if(hasAttribute(heightMapElement, "src")) {			
			String src = heightMapElement.getAttributeValue("src");
			BufferedImage image = ZipImageLoader.readImageFromZipFile(mapFile, src);
			parseHeightMap(heightMap, image, width, height);
		}
		parseOverrideValues(heightMap, heightMapElement);
		return heightMap;
	}

	private static void parseHeightMap(int[][] heightMap, BufferedImage image, int width, int height) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int rgb = image.getRGB(x, y);
				//select red channel. BufferedImage is formatted as ARGB
				rgb = rgb & 0x00FF0000;
				rgb = rgb >> 16;
				heightMap[x][y] = rgb;
			}
		}
	}
	
	private static void parseOverrideValues(int[][] heightMap, Element heightMapElement) {
		Elements tiles = heightMapElement.getChildElements();
		for(int i = 0; i < tiles.size(); i++) {
			Element tile = tiles.get(i);
			int x = Integer.parseInt(tile.getAttributeValue("x"));
			int y = Integer.parseInt(tile.getAttributeValue("y"));
			
			if(hasAttribute(tile, zTopLeft)) {
				String topLeft = tile.getAttributeValue(zTopLeft);
				int newHeight = Integer.parseInt(topLeft);
				heightMap[x][y + 1] = newHeight;
			}
			if(hasAttribute(tile, zBottomLeft)) {
				String bottomLeft = tile.getAttributeValue(zBottomLeft);
				int newHeight = Integer.parseInt(bottomLeft);
				heightMap[x][y] = newHeight;
			}
			if(hasAttribute(tile, zTopRight)) {
				String topRight = tile.getAttributeValue(zTopRight);
				int newHeight = Integer.parseInt(topRight);
				heightMap[x + 1][y + 1] = newHeight;
			}
			if(hasAttribute(tile, zBottomRight)) {
				String bottomRight = tile.getAttributeValue(zBottomRight);
				int newHeight = Integer.parseInt(bottomRight);
				heightMap[x + 1][y] = newHeight;
			}
		}
	}
	
	private static boolean hasAttribute(Element element, String attribute) {
		String value = element.getAttributeValue(attribute);
		return ((value != null) && (!value.equals("")));
	}

}
