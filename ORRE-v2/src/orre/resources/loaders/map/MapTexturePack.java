package orre.resources.loaders.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import orre.geom.Dimension2D;

import openrr.map.Orientation;
import openrr.map.WallType;
import openrr.map.soil.MapTexture;
import openrr.map.soil.MapTextureCoordinate;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureCoordinateSet;
import openrr.map.soil.SoilType;

public class MapTexturePack {
	private final HashMap<SoilType, Soil> soilMap;
	private final MapTextureSet mapTextureSet;
	private final SoilLibrary soilLibrary;
	private String currentBoundTextureName = "";
	private SoilType currentBoundSoilType;
	private WallType currentBoundWallType;
	
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
	
	public void bindTexture(SoilType soilType, WallType wallType) {
		String textureName = getTextureReferenceName(soilType, wallType);
		if(currentBoundTextureName.equals(textureName)) return;
		this.currentBoundTextureName = textureName;
		this.currentBoundSoilType = soilType;
		this.currentBoundWallType = wallType;
		MapTexture texture = mapTextureSet.getTextureByName(textureName);
		texture.bind();
	}

	private String getTextureReferenceName(SoilType soilType, WallType wallType) {
		Soil soil = soilMap.get(soilType);
		String textureName = soil.textureSet.getTexture(wallType).textureReferenceName;
		return textureName;
	}
	
	public double[] getTextureCoordinates(Orientation orientation) {
		MapTexture texture = mapTextureSet.getTextureByName(currentBoundTextureName);
		Soil soil = this.soilMap.get(this.currentBoundSoilType);
		SoilTextureCoordinateSet textureSet = soil.textureSet;
		MapTextureCoordinate coordinate = textureSet.getTexture(this.currentBoundWallType);
		
		double[] textureCoordinates = generateTextureCoordinates(texture, coordinate);
		textureCoordinates = rotateCoordinates(textureCoordinates, orientation);
		
		return textureCoordinates;
	}

	private double[] generateTextureCoordinates(MapTexture texture, MapTextureCoordinate coordinate) {
		return null;
	}

	private double[] rotateCoordinates(double[] textureCoordinates, Orientation orientation) {
		return null;
	}

	public void finalizeTextures() {
		mapTextureSet.finalizeTextures();
	}
}
