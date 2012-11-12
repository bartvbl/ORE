package orre.resources.loaders.map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import nu.xom.Element;

public class WallMapLoader {

	public static boolean[][] loadWallMap(ZipFile mapFile, Element mapDefinitionElement, int width, int height) {
		boolean[][] wallMap = new boolean[width][height];
		Element wallMapElement = mapDefinitionElement.getFirstChildElement("wallMap");
		parseWallMapImage(mapFile, wallMap, wallMapElement);
		parseOverrideRules(wallMap, wallMapElement);
		return wallMap;
	}

	private static void parseWallMapImage(ZipFile mapFile, boolean[][] wallMap, Element wallMapElement) {
		String src = wallMapElement.getAttributeValue("src");
		BufferedImage wallMapImage = readImageFile(mapFile, src);
		for(int i = 0; i < wallMap.length; i++) {
			for(int j = 0; j < wallMap[0].length; j++) {
				//System.out.println(wallMapImage.getRGB(i, j));
			}
		}
	}
	
	private static BufferedImage readImageFile(ZipFile mapFile, String src) {
		try {
			InputStream inStream = mapFile.getInputStream(mapFile.getEntry(src));
			return ImageIO.read(inStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void parseOverrideRules(boolean[][] wallMap, Element wallMapElement) {
		
	}

}
