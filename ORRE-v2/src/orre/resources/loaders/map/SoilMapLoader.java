package orre.resources.loaders.map;

import java.util.ArrayList;

import orre.resources.ResourceCache;

import nu.xom.Element;
import openrr.map.soil.Soil;

public class SoilMapLoader {

	public static Soil[][] loadSoilMap(Element mapDefinitionElement, int width, int height) {
		//1. load soil library
		ArrayList<Soil> soilLibrary = SoilLibraryBuilder.buildSoilLibrary("res/texturePack.xml");
		//2. load custom tiles
		//3. load soil map
		Soil[][] soilMap = new Soil[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				soilMap[x][y] = soilLibrary.get(0);
			}
		}
		return soilMap;
	}

}
