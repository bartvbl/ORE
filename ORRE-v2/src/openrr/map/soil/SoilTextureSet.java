package openrr.map.soil;

import openrr.map.WallType;

public class SoilTextureSet {
	private WallType[] soilTextureTypes;
	private MapTextureCoordinate[] mapTextureCoordinates;

	public SoilTextureSet() {
		this.soilTextureTypes = WallType.values();
		this.mapTextureCoordinates = new MapTextureCoordinate[soilTextureTypes.length];
	}
	
	public MapTextureCoordinate getTexture(WallType type) {
		return mapTextureCoordinates[indexOf(type)];
	}
	
	public void setTexture(WallType type, MapTextureCoordinate mapTexture) {
		mapTextureCoordinates[indexOf(type)] = mapTexture;
	}
	
	public SoilTextureSet cloneTextureSet() {
		SoilTextureSet newSet = new SoilTextureSet();
		for(int i = 0; i < soilTextureTypes.length; i++) {
			newSet.setTexture(soilTextureTypes[i], mapTextureCoordinates[i]);
		}
		return newSet;
	}
	
	private int indexOf(WallType type) {
		for(int i = 0; i < soilTextureTypes.length; i++) {
			if(type == soilTextureTypes[i]) return i;
		}
		return -1;
	}
}
