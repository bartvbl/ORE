package orre.resources.loaders.map;

import java.awt.image.BufferedImage;
import java.util.zip.ZipFile;

import nu.xom.Element;
import nu.xom.Elements;

public class WallMapLoader {
	private static final int BLACK = 0xFFFFFFFF;

	public static boolean[][] loadWallMap(ZipFile mapFile, Element mapDefinitionElement, int width, int height) {
		boolean[][] wallMap = new boolean[width][height];
		Element wallMapElement = mapDefinitionElement.getFirstChildElement("wallMap");
		parseWallMapImage(mapFile, wallMap, wallMapElement);
		parseOverrideRules(wallMap, wallMapElement);
		return wallMap;
	}

	private static void parseWallMapImage(ZipFile mapFile, boolean[][] wallMap, Element wallMapElement) {
		String src = wallMapElement.getAttributeValue("src");
		BufferedImage wallMapImage = ZipImageLoader.readImageFromZipFile(mapFile, src);
		for(int xCoord = 0; xCoord < wallMap.length; xCoord++) {
			int yCoord = 0;
			for(int j = wallMap[0].length - 1; j >= 0; j--) {
				int rgb = wallMapImage.getRGB(xCoord, j);
				boolean isWall = rgb == BLACK;
				wallMap[xCoord][yCoord] = isWall;
				yCoord++;
			}
		}
	}
	
	private static void parseOverrideRules(boolean[][] wallMap, Element wallMapElement) {
		Elements rows = wallMapElement.getChildElements();
		for(int i = 0; i < rows.size(); i++) {
			Element row = rows.get(i);
			String rowValues = row.getValue();
			int yCoord = Integer.parseInt(row.getAttributeValue("yCoord"));
			if(yCoord < 0 || yCoord > wallMap[0].length){
				if(rowValues.length() == wallMap.length - 1) {
					for(int j = 0; j < wallMap.length - 1; j++) {
						char tile = rowValues.charAt(j);
						boolean isWall = !(tile == ' ');
						wallMap[j][yCoord] = isWall;
					}
				}
			}
		}
	}
}
