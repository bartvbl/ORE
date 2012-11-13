package orre.resources.loaders.map;

import java.util.ArrayList;

import nu.xom.Element;
import openrr.map.Soil;

public class SoilMapLoader {

	public static Soil[][] loadSoilMap(Element mapDefinitionElement, int width, int height) {
		//1. load soil library
		ArrayList<Soil> soilLibrary = SoilLibraryBuilder.buildSoilLibrary("res/");
		//2. load custom tiles
		//3. load soil map
		return null;
	}

}
