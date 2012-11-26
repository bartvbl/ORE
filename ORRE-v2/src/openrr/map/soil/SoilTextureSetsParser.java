package openrr.map.soil;

import java.util.ArrayList;

import openrr.map.WallType;

import nu.xom.Element;
import nu.xom.Elements;
import orre.resources.FileToLoad;
import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;
import orre.util.XMLUtils;

public class SoilTextureSetsParser {
	public static ArrayList<SoilTextureSet> parseSoilTextureSets(Element soilTextureSets, MapTexture[] textures) {
		ArrayList<SoilTextureSet> textureSets = new ArrayList<SoilTextureSet>();
		Elements textureSetElements = soilTextureSets.getChildElements("soilTextureSet");
		for(int i = 0; i < textureSetElements.size(); i++) {
			parseTextureSet(textureSetElements.get(i), textureSets, textures);
		}
		return textureSets;
	}

	private static void parseTextureSet(Element textureSetElement, ArrayList<SoilTextureSet> textureSets, MapTexture[] textures) {
		SoilTextureSet textureSet = createSoilTextureSet(textureSetElement, textureSets);
		textureSets.add(textureSet);
		Elements texturesToParse = textureSetElement.getChildElements("texture");
		parseTextures(texturesToParse, textureSet, textures);
	}

	private static SoilTextureSet createSoilTextureSet(Element textureSetElement, ArrayList<SoilTextureSet> textureSets) {
		String soilTextureSetType = textureSetElement.getAttributeValue("type");
		SoilType soilType = Enum.valueOf(SoilType.class, soilTextureSetType);
		if(XMLUtils.hasAttribute(textureSetElement, "extends")) {
			String extendsType = textureSetElement.getAttributeValue("extends");
			for(SoilTextureSet set : textureSets) {
				if(set.type.equals(extendsType)){
					return set.clone(soilType);
				}
			}
		}
		return new SoilTextureSet(soilType);
	}

	private static void parseTextures(Elements texturesToParse, SoilTextureSet textureSet, MapTexture[] textures) {
		for(int i = 0; i < texturesToParse.size(); i++) {
			Element textureElement = texturesToParse.get(i);
			WallType type = Enum.valueOf(WallType.class, textureElement.getAttributeValue("type"));
			String textureName = textureElement.getAttributeValue("texture");
			for(int j = 0; j < textures.length; j++) {
				if(textures[j].name.equals(textureName))
					textureSet.setTexture(type, textures[j]);					
			}
		}
	}
}
