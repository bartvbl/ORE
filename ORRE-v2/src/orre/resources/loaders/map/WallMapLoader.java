package orre.resources.loaders.map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nu.xom.Element;

public class WallMapLoader {

	public static boolean[][] loadWallMap(Element mapDefinitionElement, int width, int height) {
		boolean[][] wallMap = new boolean[width][height];
		Element wallMapElement = mapDefinitionElement.getFirstChildElement("wallMap");
		parseWallMapImage(wallMap, wallMapElement);
		parseOverrideRules(wallMap, wallMapElement);
		return wallMap;
	}

	private static void parseWallMapImage(boolean[][] wallMap, Element wallMapElement) {
		String src = wallMapElement.getAttributeValue("src");
		BufferedImage wallMapImage = readImageFile(src);
		for(int i = 0; i < wallMap.length; i++) {
			for(int j = 0; j < wallMap[0].length; j++) {
				System.out.println(wallMapImage.getRGB(i, j));
			}
		}
	}
	
	private static BufferedImage readImageFile(String src) {
		try {
			return ImageIO.read(new File(src));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void parseOverrideRules(boolean[][] wallMap, Element wallMapElement) {
		
	}

}
