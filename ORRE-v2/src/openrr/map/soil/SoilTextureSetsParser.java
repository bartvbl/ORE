package openrr.map.soil;

import java.util.ArrayList;

import nu.xom.Element;
import nu.xom.Elements;
import orre.resources.FileToLoad;
import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;
import orre.util.XMLUtils;

public class SoilTextureSetsParser {
	public static ArrayList<SoilTextureSet> parseSoilTextureSets(Element soilTextureSets, String pathPrefix) {
		ArrayList<SoilTextureSet> textureSets = new ArrayList<SoilTextureSet>();
		Elements textureSetElements = soilTextureSets.getChildElements("soilTextureSet");
		for(int i = 0; i < textureSetElements.size(); i++) {
			parseTextureSet(textureSetElements.get(i), textureSets, pathPrefix);
		}
		return textureSets;
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
