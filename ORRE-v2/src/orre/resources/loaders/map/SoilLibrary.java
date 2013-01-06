package orre.resources.loaders.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import openrr.map.soil.Soil;
import openrr.map.soil.SoilType;

public class SoilLibrary {
	private HashMap<SoilType, Soil> soilMap;
	
	public SoilLibrary() {
		soilMap = new HashMap<SoilType, Soil>();
	}
	
	public void setSoil(SoilType type, Soil soil) {
		if(soilMap.containsKey(type)) {
			soilMap.remove(type);
		}
		soilMap.put(type, soil);
	}
	
	public Soil getSoilByRGB(int[] rgb) {
		Collection<Soil> soils = soilMap.values();
		for(Soil soil : soils) {
			if(Arrays.equals(soil.rgb, rgb)) {
				return soil;
			}
		}
		return null;
	}
	
	public Soil getSoilByType(SoilType type) {
		return soilMap.get(type);
	}
}
