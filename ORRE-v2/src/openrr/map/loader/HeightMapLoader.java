package openrr.map.loader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.zip.ZipFile;

import orre.geom.Dimension2D;
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
	private static final double tileHeightMultiplyer = 0.3;
	
	public static double[][] loadHeightMap(Element heightMapElement, MapLoadingContext context) throws IOException {
		Dimension2D mapSize = context.mapSize;
		
		double[][] heightMap = new double[mapSize.width + 1][mapSize.height + 1];
		
		if(hasAttribute(heightMapElement, "src")) {			
			String src = heightMapElement.getAttributeValue("src");
			BufferedImage image = ZipImageLoader.readImageFromZipFile(context.mapFile, src);
			parseHeightMap(heightMap, image, context);
		}
		parseOverrideValues(heightMap, heightMapElement);
		scaleVerticallyToBrickUnits(heightMap);
		return heightMap;
	}

	private static double[][] parseHeightMap(double[][] heightMap, BufferedImage image, MapLoadingContext context) {
		Dimension2D mapSize = context.mapSize;
		
		for(int x = 0; x < mapSize.width; x++) {
			for(int y = 0; y < mapSize.height; y++) {
				int rgb = image.getRGB(x, y);
				//BufferedImage is formatted as ARGB. I'm taking the average of the RGB channels
				int r = rgb & 0x00FF0000;
				r = r >> 16;
				int g = rgb & 0x0000FF00;
				g = g >> 8;
				int b = rgb & 0x000000FF;
				heightMap[x][y] = ((double)r + (double)g + (double)b) / 3d;
			}
		}
		
		//copy the values of the sides y = height - 1 and x = width - 1 to y = height and x = width.
		for(int i = 0; i < mapSize.width + 1; i++) {
			heightMap[i][mapSize.height] = heightMap[i][mapSize.height - 1];
		}
		for(int i = 0; i < mapSize.height + 1; i++) {
			heightMap[mapSize.width][i] = heightMap[mapSize.width - 1][i];
		}

		return heightMap;
	}
	
	private static void parseOverrideValues(double[][] heightMap, Element heightMapElement) {
		Elements tiles = heightMapElement.getChildElements();
		for(int i = 0; i < tiles.size(); i++) {
			Element tile = tiles.get(i);
			int x = Integer.parseInt(tile.getAttributeValue("x"));
			int y = Integer.parseInt(tile.getAttributeValue("y"));
			
			if(hasAttribute(tile, zTopLeft)) {
				String topLeft = tile.getAttributeValue(zTopLeft);
				double newHeight = Double.parseDouble(topLeft);
				heightMap[x][y + 1] = newHeight;
			}
			if(hasAttribute(tile, zBottomLeft)) {
				String bottomLeft = tile.getAttributeValue(zBottomLeft);
				double newHeight = Double.parseDouble(bottomLeft);
				heightMap[x][y] = newHeight;
			}
			if(hasAttribute(tile, zTopRight)) {
				String topRight = tile.getAttributeValue(zTopRight);
				double newHeight = Double.parseDouble(topRight);
				heightMap[x + 1][y + 1] = newHeight;
			}
			if(hasAttribute(tile, zBottomRight)) {
				String bottomRight = tile.getAttributeValue(zBottomRight);
				double newHeight = Double.parseDouble(bottomRight);
				heightMap[x + 1][y] = newHeight;
			}
		}
	}
	
	private static void scaleVerticallyToBrickUnits(double[][] heightMap) {
		for(int x = 0; x < heightMap.length; x++) {
			for(int y = 0; y < heightMap[0].length; y++) {
				heightMap[x][y] *= 0.375d * tileHeightMultiplyer; 
				//the 1x1 stud has sides of 1 unit. It measures 8mm. The height of the stud itself is 3mm. That gives 0.375 units. 
				
			}
		}
	}
	
	private static boolean hasAttribute(Element element, String attribute) {
		String value = element.getAttributeValue(attribute);
		return ((value != null) && (!value.equals("")));
	}
	
}
