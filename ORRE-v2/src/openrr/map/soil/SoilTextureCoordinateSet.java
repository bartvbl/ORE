package openrr.map.soil;

import openrr.map.WallType;

public class SoilTextureCoordinateSet {
	private WallType[] soilTextureTypes;
	private MapTextureCoordinate[] mapTextureCoordinates;

	public SoilTextureCoordinateSet() {
		this.soilTextureTypes = WallType.values();
		this.mapTextureCoordinates = new MapTextureCoordinate[soilTextureTypes.length];
	}
	
	public MapTextureCoordinate getTexture(WallType type) {
		int index = indexOf(type);
		if(index == -1) return null;
		return mapTextureCoordinates[index];
	}
	
	public void setTexture(WallType type, MapTextureCoordinate textureCoordinate) {
		int index = indexOf(type);
		if(index == -1) return;
		mapTextureCoordinates[index] = textureCoordinate;
	}
	
	public SoilTextureCoordinateSet cloneTextureSet() {
		SoilTextureCoordinateSet newSet = new SoilTextureCoordinateSet();
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
