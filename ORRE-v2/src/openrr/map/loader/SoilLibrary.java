package openrr.map.loader;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import openrr.map.soil.Soil;
import openrr.map.soil.SoilType;

public class SoilLibrary {
	private HashMap<SoilType, Soil> soilMap;
	
	public SoilLibrary() {
		soilMap = new HashMap<SoilType, Soil>();
	}
	
	public void setSoilTexture(SoilType type, Soil soil) {
		if(soilMap.containsKey(type)) {
			soilMap.remove(type);
		}
		soilMap.put(type, soil);
	}
	
	public SoilType getSoilTypeByRGB(int[] rgb) {
		SoilType[] soilTypes = soilMap.keySet().toArray(new SoilType[1]);
		for(SoilType soilType : soilTypes) {
			Soil soilMapEntry = this.soilMap.get(soilType);
			if(Arrays.equals(soilMapEntry.rgb, rgb)) {
				return soilType;
			}
		}
		return SoilType.DIRT;
	}
	
	public Soil getSoilByType(SoilType type) {
		return soilMap.get(type);
	}
}
