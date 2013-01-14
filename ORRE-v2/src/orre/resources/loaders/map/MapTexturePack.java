package orre.resources.loaders.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import openrr.map.soil.Soil;
import openrr.map.soil.SoilType;

public class MapTexturePack {
	private final HashMap<SoilType, Soil> soilMap;
	private final MapTextureSet mapTextureSet;
	private final SoilLibrary soilLibrary;
	
	public MapTexturePack(SoilLibrary soilLibrary, MapTextureSet mapTextureSet) {
		this.soilMap = new HashMap<SoilType, Soil>();
		this.mapTextureSet = mapTextureSet;
		this.soilLibrary = soilLibrary;
	}
	
	public void setSoilTexture(SoilType type, Soil soil) {
		if(soilMap.containsKey(type)) {
			soilMap.remove(type);
		}
		soilMap.put(type, soil);
	}
	
	public Soil getSoilByType(SoilType type) {
		return soilMap.get(type);
	}

	public void finalizeTextures() {
		mapTextureSet.finalizeTextures();
	}
}
