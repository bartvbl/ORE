package orre.resources.loaders.map;

import java.awt.image.BufferedImage;
import java.util.zip.ZipFile;

import nu.xom.Element;

public class HeightMapLoader {

	public static double[][] loadHeightMap(ZipFile mapFile, Element mapDefinitionElement, int width, int height) {
		double[][] heightMap = new double[width][height];
		Element wallMapElement = mapDefinitionElement.getFirstChildElement("heightMap");
		String src = wallMapElement.getAttributeValue("src");
		BufferedImage image = ZipImageLoader.readImageFromZipFile(mapFile, src);
		parseHeightMap(heightMap, image);
		return heightMap;
	}

	private static void parseHeightMap(double[][] heightMap, BufferedImage image) {
		for(int x = 0; x < heightMap.length; x++) {
			for(int y = 0; y < heightMap[0].length; y++) {
				int rgb = image.getRGB(x, y);
				
			}
		}
	}

}
