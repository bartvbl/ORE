package orre.resources.loaders.map;

import java.util.ArrayList;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import orre.resources.FileToLoad;
import orre.resources.ResourceCache;
import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;
import orre.util.XMLLoader;
import orre.util.XMLUtils;

import openrr.map.soil.DirtSoil;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilTextureType;

public class SoilLibraryBuilder {
	public static ArrayList<Soil> buildSoilLibrary(String src) {
		ArrayList<Soil> soilList = new ArrayList<Soil>();
		
		Document texturePackXML = XMLLoader.readMapXML(src);
		Element rootElement = texturePackXML.getRootElement();
		Element soilTextureSets = rootElement.getFirstChildElement("soilTextureSets");
		
		Element config = rootElement.getFirstChildElement("config");
		Element pathPrefixElement = config.getFirstChildElement("pathPrefix");
		String pathPrefix = pathPrefixElement.getAttributeValue("value");
		
		parseSoilTextureSets(soilList, soilTextureSets, pathPrefix);
		
		return soilList;
	}

	private static void parseSoilTextureSets(ArrayList<Soil> soilList, Element soilTextureSets, String pathPrefix) {
		ArrayList<SoilTextureSet> textureSets = new ArrayList<SoilTextureSet>();
		Elements textureSetElements = soilTextureSets.getChildElements("soilTextureSet");
		for(int i = 0; i < textureSetElements.size(); i++) {
			parseTextureSet(textureSetElements.get(i), textureSets, pathPrefix);
		}
	}

	private static void parseTextureSet(Element textureSetElement, ArrayList<SoilTextureSet> textureSets, String pathPrefix) {
		SoilTextureSet textureSet = createSoilTextureSet(textureSetElement, textureSets);
		textureSets.add(textureSet);
		Elements texturesToParse = textureSetElement.getChildElements("texture");
		parseTextures(texturesToParse, textureSet, pathPrefix);
	}

	private static SoilTextureSet createSoilTextureSet(Element textureSetElement, ArrayList<SoilTextureSet> textureSets) {
		String soilTextureSetType = textureSetElement.getAttributeValue("type");
		if(XMLUtils.hasAttribute(textureSetElement, "extends")) {
			String extendsType = textureSetElement.getAttributeValue("extends");
			for(SoilTextureSet set : textureSets) {
				if(set.type.equals(extendsType)){
					return set.clone(soilTextureSetType);
				}
			}
		}
		return new SoilTextureSet(soilTextureSetType);
	}

	private static void parseTextures(Elements texturesToParse, SoilTextureSet textureSet, String pathPrefix) {
		for(int i = 0; i < texturesToParse.size(); i++) {
			Element textureElement = texturesToParse.get(i);
			SoilTextureType type = Enum.valueOf(SoilTextureType.class, textureElement.getAttributeValue("type"));
			String textureSrc = textureElement.getAttributeValue("src");
			PartiallyLoadableTexture texture = TextureLoader.partiallyLoadTextureFromFile(new FileToLoad(pathPrefix, textureSrc));
			textureSet.setTexture(type, texture);
		}
	}
}
