package orre.resources.loaders.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import orre.entity.Entity;

import nu.xom.Element;
import nu.xom.Elements;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureCoordinateSet;
import openrr.map.soil.SoilType;

//TODO: refit the textures of a texture set so that they carry coordinates rather than Texture objects

public class TexturePackParser {
	public static SoilLibrary parseTexturePackXML(Element rootElement) {
		Element soilTextureSetsRootElement = rootElement.getFirstChildElement("soilTextureSets");
		SoilLibrary soilLibrary = parseSoilTextureSets(soilTextureSetsRootElement);
		return soilLibrary;
	}

	private static SoilLibrary parseSoilTextureSets(Element soilTextureSetsRootElement) {
		HashMap<String, SoilTextureCoordinateSet> parsedTextureSets = new HashMap<String, SoilTextureCoordinateSet>();
		HashMap<String, int[]> soilRGBValues = new HashMap<String, int[]>();
		Elements soilTextureSets = soilTextureSetsRootElement.getChildElements();
		for(int i = 0; i < soilTextureSets.size(); i++) {
			Element soilTextureSetElement = soilTextureSets.get(i);
			parseTextureSet(parsedTextureSets, soilRGBValues, soilTextureSetElement);
		}
		SoilLibrary soilLibrary = copyValidTextureSets(parsedTextureSets, soilRGBValues);
		
		return soilLibrary;
	}

	private static void parseTextureSet(HashMap<String, SoilTextureCoordinateSet> parsedTextureSets, HashMap<String, int[]> soilRGBValues, Element soilTextureSetElement) {
		SoilTextureCoordinateSet textureSet = createNewTextureSet(soilTextureSetElement, parsedTextureSets);
		TextureSetParser.parseTextureSet(textureSet, soilTextureSetElement);
		
		String textureSetName = soilTextureSetElement.getAttributeValue("type");
		parsedTextureSets.put(textureSetName, textureSet);
		
		int[] soilMapRGBColour = parseSoilMapRGBColour(soilTextureSetElement);
		soilRGBValues.put(textureSetName, soilMapRGBColour);
	}
	
	private static int[] parseSoilMapRGBColour(Element soilTextureSetElement) {
		String redStringValue = soilTextureSetElement.getAttributeValue("r");
		String greenStringValue = soilTextureSetElement.getAttributeValue("g");
		String blueStringValue = soilTextureSetElement.getAttributeValue("b");
		
		int redValue = Integer.parseInt(redStringValue);
		int greenValue = Integer.parseInt(greenStringValue);
		int blueValue = Integer.parseInt(blueStringValue);
		
		return new int[]{redValue, greenValue, blueValue};
	}

	private static SoilTextureCoordinateSet createNewTextureSet(Element soilTextureSetElement, HashMap<String, SoilTextureCoordinateSet> parsedTextureSets) {
		String parentSoilType = soilTextureSetElement.getAttributeValue("extends");
		if(parentSoilType != null) {
			SoilTextureCoordinateSet textureSet = parsedTextureSets.get(parentSoilType);
			if(textureSet != null) {
				return textureSet.cloneTextureSet();
			} else {
				return new SoilTextureCoordinateSet();
			}
		} else {
			return new SoilTextureCoordinateSet();
		}
	}

	private static SoilLibrary copyValidTextureSets(HashMap<String, SoilTextureCoordinateSet> parsedTextureSets, HashMap<String, int[]> soilRGBValues) {
		SoilLibrary soilLibrary = new SoilLibrary();
		for(SoilType soilType : SoilType.values()) {
			String soilTextureSetName = soilType.toString();
			SoilTextureCoordinateSet textureSet = parsedTextureSets.get(soilTextureSetName);
			int[] soilMapRGBColour = soilRGBValues.get(soilTextureSetName);
			if(textureSet != null) {
				Soil soil = new Soil(textureSet, soilMapRGBColour);
				soilLibrary.setSoil(soilType, soil);
			}
		}
		return soilLibrary;
	}
}
