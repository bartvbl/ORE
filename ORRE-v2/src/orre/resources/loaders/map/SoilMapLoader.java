package orre.resources.loaders.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipFile;

import orre.resources.ResourceCache;

import nu.xom.Element;
import openrr.map.soil.Soil;

public class SoilMapLoader {

	public static Soil[][] loadSoilMap(ZipFile mapFile, Element mapDefinitionElement, int width, int height) {
		//1. load soil library
		ArrayList<Soil> soilLibrary = SoilLibraryBuilder.buildSoilLibrary("res/texturePack.xml");
		//2. load custom tiles
		//TODO: implement loading of custom tiles
		//3. load soil map
		Element soilMapElement = mapDefinitionElement.getFirstChildElement("soilMap");
		String mapImageSrc = soilMapElement.getAttributeValue("src");
		BufferedImage soilMapImage = ZipImageLoader.readImageFromZipFile(mapFile, mapImageSrc);
		Soil[][] soilMap = new Soil[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int argb = soilMapImage.getRGB(x, y);
				soilMap[x][y] = getSoilByARGB(argb, soilLibrary);
			}
		}
		return soilMap;
	}
	
	private static Soil getSoilByARGB(int argb, ArrayList<Soil> soilLibrary) {
		int r = argb & 0x00FF0000;
		r = r >> 16;
		int g = argb & 0x0000FF00;
		g = g >> 8;
		int b = argb & 0x000000FF;
		int[] rgb = new int[]{r, g, b};
		for(Soil soil : soilLibrary) {
			if(Arrays.equals(rgb, soil.rgb)) {
				return soil;
			}
		}
		return soilLibrary.get(0);
	}

}
