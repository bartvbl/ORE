package orre.resources.loaders.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import openrr.map.soil.Soil;
import openrr.map.soil.SoilType;

public class SoilLibraryBuilder {

	public static HashMap<SoilType, Soil> buildSoilLibrary() {
		//loading the default texture pack
		HashMap<SoilType, Soil> defaultSoilLibrary = parseTexturePack("res/texturePack.xml");
		//for supporting texture packs that can override the default one, parse the other texture pack file after loading the default one.
		//then overwrite those entries in the existing soil library that are defined in the overriding texture pack.
		return defaultSoilLibrary;
	}

	private static HashMap<SoilType, Soil> parseTexturePack(String src) {
		Element rootElement = readTexturePackXML(src);
		HashMap<SoilType, Soil> soilLibrary = TexturePackParser.parseTexturePackXML(rootElement);
		return soilLibrary;
	}

	private static Element readTexturePackXML(String src) {
		Builder builder = new Builder();
		try {
			Document doc = builder.build(src);
			return doc.getRootElement();
		} catch (ValidityException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
