package openrr.map.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import orre.resources.ResourceType;
import orre.resources.UnloadedResource;
import orre.resources.loaders.TextureLoader;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;
import nu.xom.Element;
import nu.xom.Elements;
import openrr.map.soil.MapTexture;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureCoordinateSet;
import openrr.map.soil.SoilType;

public class TexturePackParser {
	public static MapTexturePack parseTexturePackXML(Element rootElement, MapLoadingContext context) throws FileNotFoundException, IOException, Exception {
		Element configElement = rootElement.getFirstChildElement("config");
		String pathPrefix = parsePathPrefix(configElement);
		Element texturesElement = rootElement.getFirstChildElement("textures");
		MapTextureSet mapTextures = parseMapTextures(texturesElement, pathPrefix);
		
		Element soilTextureSetsRootElement = rootElement.getFirstChildElement("soilTextureSets");
		SoilLibrary soilLibrary = parseSoilTextureSets(soilTextureSetsRootElement);
		context.soilLibrary = soilLibrary;
		
		MapTexturePack texturePack = new MapTexturePack(soilLibrary, mapTextures);
		return texturePack;
	}

	private static String parsePathPrefix(Element configElement) {
		Element pathPrefixElement = configElement.getFirstChildElement("pathPrefix");
		return pathPrefixElement.getAttributeValue("value");
	}

	private static MapTextureSet parseMapTextures(Element texturesElement, String pathPrefix) throws FileNotFoundException, IOException, Exception {
		Elements textureElements = texturesElement.getChildElements();
		MapTextureSet textureSet = new MapTextureSet();
		for(int i = 0; i < textureElements.size(); i++) {
			Element textureElement = textureElements.get(i);
			MapTexture mapTexture = parseMapTextureFromXML(textureElement, pathPrefix);
			textureSet.addTexture(mapTexture);
		}
		return textureSet;
	}

	private static MapTexture parseMapTextureFromXML(Element textureElement, String pathPrefix) throws FileNotFoundException, IOException, Exception {
		String name = textureElement.getAttributeValue("name");
		String textureSrc = textureElement.getAttributeValue("src");
		PartiallyLoadableTexture texture = TextureLoader.partiallyLoadTextureFromFile(new UnloadedResource(ResourceType.texture, new File(pathPrefix + "/" + textureSrc), "Texture pack texture"));
		String widthInTexturesString = textureElement.getAttributeValue("widthInTextures");
		String heightInTexturesString = textureElement.getAttributeValue("heightInTextures");
		int widthInTextures = Integer.parseInt(widthInTexturesString);
		int heightInTextures = Integer.parseInt(heightInTexturesString);
		MapTexture mapTexture = new MapTexture(name, texture, widthInTextures, heightInTextures);
		return mapTexture;
	}

	private static SoilLibrary parseSoilTextureSets(Element soilTextureSetsRootElement) {
		HashMap<String, SoilTextureCoordinateSet> parsedTextureSets = new HashMap<String, SoilTextureCoordinateSet>();
		HashMap<String, int[]> soilRGBValues = new HashMap<String, int[]>();
		Elements soilTextureSets = soilTextureSetsRootElement.getChildElements();
		for(int i = 0; i < soilTextureSets.size(); i++) {
			Element soilTextureSetElement = soilTextureSets.get(i);
			parseTextureSet(parsedTextureSets, soilRGBValues, soilTextureSetElement);
		}
		SoilLibrary texturePack = copyValidTextureSets(parsedTextureSets, soilRGBValues);
		return texturePack;
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
				soilLibrary.setSoilTexture(soilType, soil);
			}
		}
		return soilLibrary;
	}
}
