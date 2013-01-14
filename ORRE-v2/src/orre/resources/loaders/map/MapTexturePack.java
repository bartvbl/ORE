package orre.resources.loaders.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import openrr.map.soil.Soil;
import openrr.map.soil.SoilType;

public class MapTexturePack {
	private HashMap<SoilType, Soil> soilMap;
	private MapTextureSet mapTextureSet;
	
	public MapTexturePack(SoilLibrary soilLibrary, MapTextureSet mapTextureSet) {
		soilMap = new HashMap<SoilType, Soil>();
		this.mapTextureSet = mapTextureSet;
	}
	
	public void setSoilTexture(SoilType type, Soil soil) {
		if(soilMap.containsKey(type)) {
			soilMap.remove(type);
		}
		soilMap.put(type, soil);
	}
	
	public SoilType getSoilTypeByRGB(int[] rgb) {
		Set<SoilType> keySet = soilMap.keySet();
		int numEntriesInKeySet = keySet.size();
		SoilType[] soilTypes = keySet.toArray(new SoilType[numEntriesInKeySet]);
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

	public void finalizeTextures() {
		mapTextureSet.finalizeTextures();
	}
}
