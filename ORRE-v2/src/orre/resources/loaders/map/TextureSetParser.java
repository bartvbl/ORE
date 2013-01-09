package orre.resources.loaders.map;

import nu.xom.Element;
import nu.xom.Elements;
import openrr.map.WallType;
import openrr.map.soil.MapTextureCoordinate;
import openrr.map.soil.SoilTextureCoordinateSet;

public class TextureSetParser {

	public static void parseTextureSet(SoilTextureCoordinateSet textureSet, Element textureSetElement) {
		Elements textureElements = textureSetElement.getChildElements();
		for(int i = 0; i < textureElements.size(); i++) {
			Element textureElement = textureElements.get(i);
			String textureType = textureElement.getAttributeValue("type");
			if(textureTypeIsValid(textureType)) {
				WallType wallType = WallType.valueOf(textureType);
				MapTextureCoordinate textureCoordinate = parseTextureElement(textureElement);
				addTextureCoordinateToTextureSet(textureCoordinate, wallType, textureSet);				
			}
		}
	}

	private static boolean textureTypeIsValid(String textureType) {
		WallType[] wallTypes = WallType.values();
		for(WallType wallType : wallTypes) {
			String wallTypeName = wallType.toString();
			if(textureType.equals(wallTypeName)) {
				return true;
			}
		}
		return false;
	}

	private static MapTextureCoordinate parseTextureElement(Element textureElement) {
		String xString = textureElement.getAttributeValue("x");
		String yString = textureElement.getAttributeValue("y");
		String textureReferenceName = textureElement.getAttributeValue("texture");
		
		int x = Integer.parseInt(xString);
		int y = Integer.parseInt(yString);
		
		return new MapTextureCoordinate(textureReferenceName, x, y);
	}
	
	private static void addTextureCoordinateToTextureSet(MapTextureCoordinate textureCoordinate, WallType wallType, SoilTextureCoordinateSet textureSet) {
		textureSet.setTexture(wallType, textureCoordinate);
	}
}
