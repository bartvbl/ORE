package orre.resources.loaders.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import orre.entity.Entity;

import nu.xom.Element;
import nu.xom.Elements;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

//TODO: refit the textures of a texture set so that they carry coordinates rather than Texture objects

public class TexturePackParser {
	public static SoilLibrary parseTexturePackXML(Element rootElement) {
		Element soilTextureSetsRootElement = rootElement.getFirstChildElement("soilTextureSets");
		SoilLibrary soilLibrary = parseSoilTextureSets(soilTextureSetsRootElement);
		return soilLibrary;
	}

	private static SoilLibrary parseSoilTextureSets(Element soilTextureSetsRootElement) {
		HashMap<String, SoilTextureSet> parsedTextureSets = new HashMap<String, SoilTextureSet>();
		HashMap<String, int[]> soilRGBValues = new HashMap<String, int[]>();
		Elements soilTextureSets = soilTextureSetsRootElement.getChildElements();
		for(int i = 0; i < soilTextureSets.size(); i++) {
			Element soilTextureSetElement = soilTextureSets.get(i);
			SoilTextureSet textureSet = createNewTextureSet(soilTextureSetElement, parsedTextureSets);
			int[] soilMapRGBColour = parseSoilMapRGBColour(soilTextureSetElement);
			parseSoilTextureSet(textureSet, soilTextureSetElement);
			
			String textureSetName = soilTextureSetElement.getAttributeValue("type");
			parsedTextureSets.put(textureSetName, textureSet);
			soilRGBValues.put(textureSetName, soilMapRGBColour);
		}
		SoilLibrary soilLibrary = copyValidTextureSets(parsedTextureSets, soilRGBValues);
		
		return soilLibrary;
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

	private static SoilTextureSet createNewTextureSet(Element soilTextureSetElement, HashMap<String, SoilTextureSet> parsedTextureSets) {
		String parentSoilType = soilTextureSetElement.getAttributeValue("extends");
		if(parentSoilType != null) {
			SoilTextureSet textureSet = parsedTextureSets.get(parentSoilType);
			if(textureSet != null) {
				return textureSet.cloneTextureSet();
			} else {
				return new SoilTextureSet();
			}
		} else {
			return new SoilTextureSet();
		}
	}

	private static SoilLibrary copyValidTextureSets(HashMap<String, SoilTextureSet> parsedTextureSets, HashMap<String, int[]> soilRGBValues) {
		SoilLibrary soilLibrary = new SoilLibrary();
		for(SoilType soilType : SoilType.values()) {
			String soilTextureSetName = soilType.toString();
			SoilTextureSet textureSet = parsedTextureSets.get(soilTextureSetName);
			int[] soilMapRGBColour = soilRGBValues.get(soilTextureSetName);
			if(textureSet != null) {
				Soil soil = new Soil(textureSet, soilMapRGBColour);
				soilLibrary.setSoil(soilType, soil);
			}
		}
		return soilLibrary;
	}

	private static boolean soilTypeNameExists(String soilName) {
		SoilType[] soilTypes = SoilType.values();
		for(SoilType soilType : soilTypes) {
			if(soilName.equals(soilType)) {
				return true;
			}
		}
		return false;
	}

	private static void parseSoilTextureSet(SoilTextureSet textureSet, Element element) {
		
	}
}
