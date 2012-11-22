package orre.resources.loaders.map;

import java.util.ArrayList;

import orre.resources.ResourceCache;

import openrr.map.Soil;
import openrr.map.soil.DirtSoil;

public class SoilLibraryBuilder {
//TODO: create the soil library loader. 
	public static ArrayList<Soil> buildSoilLibrary(String src) {
		ArrayList<Soil> soilList = new ArrayList<Soil>();
		
		soilList.add(new DirtSoil());
		
		return soilList;
	}

}
