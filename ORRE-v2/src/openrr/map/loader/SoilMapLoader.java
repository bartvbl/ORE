package openrr.map.loader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.ZipFile;

import orre.geom.Dimension2D;
import orre.resources.ResourceCache;
import nu.xom.Element;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilType;

public class SoilMapLoader {

	public static SoilType[][] loadSoilMap(Element soilMapElement, MapLoadingContext context) throws IOException {
		
		//1. load custom tiles
		
		//TODO: implement loading of custom tiles
		//2. load soil map
		
		String mapImageSrc = soilMapElement.getAttributeValue("src");
		Dimension2D mapSize = context.mapSize;
		BufferedImage soilMapImage = ZipImageLoader.readImageFromZipFile(context.mapFile, mapImageSrc);
		SoilType[][] soilMap = new SoilType[mapSize.width][mapSize.height];
		for(int x = 0; x < mapSize.width; x++) {
			for(int y = 0; y < mapSize.height; y++) {
				int argb = soilMapImage.getRGB(x, y);
				soilMap[x][y] = getSoilByARGB(argb, context.soilLibrary);
			}
		}
		return soilMap;
	}
	
	private static SoilType getSoilByARGB(int argb, SoilLibrary soilLibrary) {
		int r = argb & 0x00FF0000;
		r = r >> 16;
		int g = argb & 0x0000FF00;
		g = g >> 8;
		int b = argb & 0x000000FF;
		int[] rgb = new int[]{r, g, b};
		return soilLibrary.getSoilTypeByRGB(rgb);
	}

}
