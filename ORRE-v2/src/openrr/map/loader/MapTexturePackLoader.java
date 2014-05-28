package openrr.map.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import orre.util.XMLLoader;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilType;

public class MapTexturePackLoader {

	public static MapTexturePack buildTexturePack(MapLoadingContext context) throws FileNotFoundException, IOException, Exception {
		//loading the default texture pack
		String workingDirectory = System.getProperty("user.dir");
		MapTexturePack defaultTexturePack = parseTexturePack(new File(workingDirectory + "/res/texturePack.xml"), context);
		//for supporting texture packs that can override the default one, parse the other texture pack file after loading the default one.
		//then overwrite those entries in the existing soil library that are defined in the overriding texture pack.
		return defaultTexturePack;
	}

	private static MapTexturePack parseTexturePack(File src, MapLoadingContext context) throws FileNotFoundException, IOException, Exception {
		Element rootElement = readTexturePackXML(src);
		MapTexturePack soilLibrary = TexturePackParser.parseTexturePackXML(rootElement, context);
		return soilLibrary;
	}

	private static Element readTexturePackXML(File src) {
		//temporary code to make sure the file exists
		boolean fileExists = src.exists();
		if(fileExists) System.out.println("The texture pack file exists.");
		else System.out.println("The system can not find the texture pack file, which should be located at: " + src + ".");
		//end temporary code
		
		Document document = XMLLoader.readXML(src);
		return document.getRootElement();
	}

}
