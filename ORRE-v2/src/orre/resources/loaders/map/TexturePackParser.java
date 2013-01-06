package orre.resources.loaders.map;

import java.util.ArrayList;
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
		Elements soilTextureSets = soilTextureSetsRootElement.getChildElements();
		for(int i = 0; i < soilTextureSets.size(); i++) {
			Element soilTextureSetElement = soilTextureSets.get(i);
			SoilTextureSet textureSet = createNewTextureSet(soilTextureSetElement, parsedTextureSets);
			parseSoilTextureSet(textureSet, soilTextureSetElement);
			String textureSetName = soilTextureSetElement.getAttributeValue("type");
			parsedTextureSets.put(textureSetName, textureSet);
		}
		SoilLibrary soilLibrary = copyValidTextureSets(parsedTextureSets);
		
		return soilLibrary;
	}

	private static SoilLibrary copyValidTextureSets(HashMap<String, SoilTextureSet> parsedTextureSets) {
		SoilLibrary soilLibrary = new SoilLibrary();
		for(SoilType soilType : SoilType.values()) {
			SoilTextureSet textureSet = parsedTextureSets.get(soilType.toString());
			if(textureSet != null) {
				Soil soil = new Soil(textureSet, new int[]{1,2,3});
				soilLibrary.setSoil(soilType, soil);
			}
		}
		return soilLibrary;
	}
	
	//Soil parseSoilElement()
		//SoilTextureSet parseSoilTextureSet()

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
