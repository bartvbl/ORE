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
	private final double[] textureCoordinateArray; //an array that is reused to avoid object allocation spam
	
	public MapTexturePack(SoilLibrary soilLibrary, MapTextureSet mapTextureSet) {
		this.mapTextureSet = mapTextureSet;
		this.soilLibrary = soilLibrary;
		this.textureCoordinateArray = new double[4];
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
	
	public double[] getTextureCoordinates(Orientation orientation) {
		MapTexture texture = mapTextureSet.getTextureByName(currentBoundTextureName);
		Soil soil = this.soilLibrary.getSoilByType(currentBoundSoilType);
		SoilTextureCoordinateSet textureSet = soil.textureSet;
		MapTextureCoordinate coordinate = textureSet.getTexture(this.currentBoundWallType);
		double[] textureCoordinates = generateTextureCoordinates(texture, coordinate);
		textureCoordinates = rotateCoordinates(textureCoordinates, orientation);
		
		return textureCoordinates;
	}
	
	private double[] generateTextureCoordinates(MapTexture texture, MapTextureCoordinate coordinate) {
		/* u1 */ textureCoordinateArray[0] = (double)coordinate.x / (double)texture.widthInTextures;
		/* u2 */ textureCoordinateArray[1] = (double)coordinate.y / (double)texture.heightInTextures;
		/* v1 */ textureCoordinateArray[2] = (double)(coordinate.x + 1) / (double)texture.widthInTextures;
		/* v2 */ textureCoordinateArray[3] = (double)(coordinate.y + 1) / (double)texture.heightInTextures;
		return textureCoordinateArray;
	}
	
	private double[] rotateCoordinates(double[] textureCoordinates, Orientation orientation) {
		if(orientation == Orientation.west) {
			//switched out the two u coordinates
			return new double[]{textureCoordinates[2],
								textureCoordinates[1],
								textureCoordinates[0],
								textureCoordinates[3]};
		} else if(orientation == Orientation.east) {
			//switched out the two v coordinates
			return new double[]{textureCoordinates[0],
								textureCoordinates[3],
								textureCoordinates[2],
								textureCoordinates[1]};
		} else if(orientation == Orientation.south) {
			//switched out both the u and v coordinates
			return new double[]{textureCoordinates[2],
								textureCoordinates[3],
								textureCoordinates[0],
								textureCoordinates[1]};
		}
		return textureCoordinates;
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
