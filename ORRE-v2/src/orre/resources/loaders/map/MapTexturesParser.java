package orre.resources.loaders.map;

import orre.resources.FileToLoad;
import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;
import nu.xom.Element;
import nu.xom.Elements;
import openrr.map.soil.MapTexture;

public class MapTexturesParser {

	public static MapTexture[] parseMapTextures(Element texturesElement, String pathPrefix) {
		MapTexture[] textures = new MapTexture[texturesElement.getChildCount()];
		Elements textureElements = texturesElement.getChildElements();
		for(int i = 0; i < textureElements.size(); i++) {
			Element textureElement = textureElements.get(i);
			String name = textureElement.getAttributeValue("name");
			String src = textureElement.getAttributeValue("src");
			PartiallyLoadableTexture texture = TextureLoader.partiallyLoadTextureFromFile(new FileToLoad(pathPrefix, src));
			textures[i] = new MapTexture(name, texture);
		}
		return textures;
	}
}
