package orre.resources.loaders.map;

import java.util.ArrayList;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import orre.util.XMLLoader;
import openrr.map.soil.MapTexture;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilTextureSetsParser;
import openrr.map.soil.SoilType;
import openrr.map.soil.soilTypes.SoilCreator;

public class SoilLibraryBuilder {
	public static ArrayList<Soil> buildSoilLibrary(String src) {
		
		Document texturePackXML = XMLLoader.readMapXML(src);
		Element rootElement = texturePackXML.getRootElement();
		Element soilTextureSets = rootElement.getFirstChildElement("soilTextureSets");
		Element config = rootElement.getFirstChildElement("config");
		Element mapTextures = rootElement.getFirstChildElement("textures");
		
		Element pathPrefixElement = config.getFirstChildElement("pathPrefix");
		String pathPrefix = pathPrefixElement.getAttributeValue("value");
		
		MapTexture[] textures = MapTexturesParser.parseMapTextures(mapTextures, pathPrefix);
		
		ArrayList<SoilTextureSet> textureSets = SoilTextureSetsParser.parseSoilTextureSets(soilTextureSets, textures);
		
		ArrayList<Soil> soilList = parseSoilList(textureSets, soilTextureSets);
		
		return soilList;
	}

	private static ArrayList<Soil> parseSoilList(ArrayList<SoilTextureSet> textureSets, Element soilTextureSets) {
		ArrayList<Soil> soilList = new ArrayList<Soil>();
		Elements soilTypeElements = soilTextureSets.getChildElements();
		for(int i = 0; i < soilTypeElements.size(); i++) {
			parseSoilElement(soilTypeElements.get(i), soilList, textureSets);
		}
		return soilList;
	}

	private static void parseSoilElement(Element element, ArrayList<Soil> soilList, ArrayList<SoilTextureSet> textureSets) {
		SoilType type;
		try {
			type = Enum.valueOf(SoilType.class, element.getAttributeValue("type"));
		}
		catch(IllegalArgumentException e) {
			System.out.println("unknown tile type in texture pack file (not necessarily a problem): " + element.getAttributeValue("type"));
			return;
		}
		int r = Integer.parseInt(element.getAttributeValue("r"));
		int g = Integer.parseInt(element.getAttributeValue("g"));
		int b = Integer.parseInt(element.getAttributeValue("b"));
		int[] rgb = new int[]{r, g, b};
		SoilTextureSet textureSet = textureSets.get(0);
		for(int i = 0; i < textureSets.size(); i++) {
			if(textureSets.get(i).type == type)
				textureSet = textureSets.get(i);
		}
		Soil soil = SoilCreator.buildSoilInstance(type, textureSet, rgb);
		soilList.add(soil);
	}
}
