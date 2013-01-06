package orre.resources.loaders.map;

import java.util.ArrayList;
import java.util.HashMap;

import nu.xom.Element;
import nu.xom.Elements;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class TexturePackParser {
	public static HashMap<SoilType, Soil> parseTexturePackXML(Element rootElement) {
		
		Element soilTextureSetsRootElement = rootElement.getFirstChildElement("soilTextureSets");
		HashMap<SoilType, Soil> soilLibrary = parseSoilTextureSets(soilTextureSetsRootElement);
		return soilLibrary;
	}

	private static HashMap<SoilType, Soil> parseSoilTextureSets(Element soilTextureSetsRootElement) {
		HashMap<String, SoilTextureSet> parsedTextureSets = new HashMap<String, SoilTextureSet>();
		Elements soilTextureSets = soilTextureSetsRootElement.getChildElements();
		for(int i = 0; i < soilTextureSets.size(); i++) {
			Element soilTextureSetElement = soilTextureSets.get(i);
			SoilTextureSet textureSet = parseSoilTextureSet(soilTextureSetElement);
			String textureSetName = soilTextureSetElement.getAttributeValue("type");
			parsedTextureSets.put(textureSetName, textureSet);
			
		}
		HashMap<SoilType, Soil> soilLibrary = new HashMap<SoilType, Soil>();
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

	private static SoilTextureSet parseSoilTextureSet(Element element) {
		SoilTextureSet textureSet = new SoilTextureSet();
		return textureSet;
	}
}
