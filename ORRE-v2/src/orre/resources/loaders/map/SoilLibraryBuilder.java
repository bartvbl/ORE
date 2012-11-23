package orre.resources.loaders.map;

import java.util.ArrayList;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import orre.resources.ResourceCache;
import orre.util.XMLLoader;

import openrr.map.soil.DirtSoil;
import openrr.map.soil.Soil;

public class SoilLibraryBuilder {
	public static ArrayList<Soil> buildSoilLibrary(String src) {
		ArrayList<Soil> soilList = new ArrayList<Soil>();
		
		Document texturePackXML = XMLLoader.readMapXML(src);
		Element rootElement = texturePackXML.getRootElement();
		Element soilTextureSets = rootElement.getFirstChildElement("soilTextureSets");
		
		parseSoilTextureSets(soilList, soilTextureSets);
		
		return soilList;
	}

	private static void parseSoilTextureSets(ArrayList<Soil> soilList, Element soilTextureSets) {
		Elements textureSets = soilTextureSets.getChildElements("soilTextureSet");
		for(int i = 0; i < textureSets.size(); i++) {
			parseTextureSet(textureSets.get(i), soilList);
		}
	}

	private static void parseTextureSet(Element textureSet, ArrayList<Soil> soilList) {
		
	}

}
