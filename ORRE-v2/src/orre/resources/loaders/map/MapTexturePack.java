package orre.resources.loaders.map;

import java.util.Arrays;
import java.util.HashMap;
import orre.gl.materials.Material;

import openrr.map.Orientation;
import openrr.map.WallType;
import openrr.map.soil.MapTexture;
import openrr.map.soil.MapTextureCoordinate;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureCoordinateSet;
import openrr.map.soil.SoilType;

public class MapTexturePack {
	private final MapTextureSet mapTextureSet;
	private final SoilLibrary soilLibrary;
	private String currentBoundTextureName = "";
	private SoilType currentBoundSoilType;
	private WallType currentBoundWallType;
	
	public MapTexturePack(SoilLibrary soilLibrary, MapTextureSet mapTextureSet) {
		this.mapTextureSet = mapTextureSet;
		this.soilLibrary = soilLibrary;
	}
	
	public void setSoilTexture(SoilType type, Soil soil) {
		soilLibrary.setSoilTexture(type, soil);
	}
	
	public void bindTexture(SoilType soilType, WallType wallType) {
		if(isBound(soilType, wallType)) return;
		String textureName = getTextureReferenceName(soilType, wallType);
		this.currentBoundTextureName = textureName;
		this.currentBoundSoilType = soilType;
		this.currentBoundWallType = wallType;
		MapTexture texture = mapTextureSet.getTextureByName(textureName);
		texture.bind();
	}

	private String getTextureReferenceName(SoilType soilType, WallType wallType) {
		Soil soil = soilLibrary.getSoilByType(soilType);
		String textureName = soil.textureSet.getTexture(wallType).textureReferenceName;
		return textureName;
	}
	
	public double[][] getTextureCoordinates(Orientation orientation) {
		MapTexture texture = mapTextureSet.getTextureByName(currentBoundTextureName);
		Soil soil = this.soilLibrary.getSoilByType(currentBoundSoilType);
		SoilTextureCoordinateSet textureSet = soil.textureSet;
		MapTextureCoordinate coordinate = textureSet.getTexture(this.currentBoundWallType);
		double[][] textureCoordinates2x2 = generateTextureCoordinates(texture, coordinate);
		textureCoordinates2x2 = MapTextureCoordinateRotator.rotateCoordinates(textureCoordinates2x2, orientation, this.currentBoundWallType);
		return textureCoordinates2x2;
	}
	
	private double[][] generateTextureCoordinates(MapTexture texture, MapTextureCoordinate coordinate) {
		double[][] textureCoordArray2x2 = new double[2][2];
		/* u1 */ textureCoordArray2x2[0][0] = (double)coordinate.x / (double)texture.widthInTextures;
		/* u2 */ textureCoordArray2x2[0][1] = (double)coordinate.y / (double)texture.heightInTextures;
		/* v1 */ textureCoordArray2x2[1][0] = (double)(coordinate.x + 1) / (double)texture.widthInTextures;
		/* v2 */ textureCoordArray2x2[1][1] = (double)(coordinate.y + 1) / (double)texture.heightInTextures;
		return textureCoordArray2x2;
	}

	public Material generateBoundTextureMaterial() {
		MapTexture currentBoundTexture = mapTextureSet.getTextureByName(this.currentBoundTextureName);
		return currentBoundTexture.generateTextureMaterial();
	}
	
	public boolean isBound(SoilType soilType, WallType wallType) {
		return (soilType == this.currentBoundSoilType) && (wallType == this.currentBoundWallType);
	}

	public void finalizeTextures() {
		mapTextureSet.finalizeTextures();
	}
}
