package orre.resources.loaders.map;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import openrr.map.Map;
import openrr.map.MapTile;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import orre.resources.FileToLoad;

public class MapLoader {

	public static PartiallyLoadableMap loadMap(FileToLoad currentFile) {
		String mapSrc = currentFile.getPath();
		ZipFile mapFile = openMap(mapSrc);
		Document mapXML = readMapXML(mapFile);
		MapLoadingContext context = new MapLoadingContext(mapXML, mapFile);
		PartiallyLoadableMap map = parseMapXML(context);
		return map;
	}
	
	private static ZipFile openMap(String src) {
		try {
			ZipFile mapFile = new ZipFile(src);
			return mapFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	private static Document readMapXML(ZipFile mapFile) {
		Builder builder = new Builder();
		try {
			ZipEntry mainMapXMLEntry = mapFile.getEntry("map.xml");
			return builder.build(mapFile.getInputStream(mainMapXMLEntry));
		}
		catch (ValidityException e) { e.printStackTrace(); }
		catch (ParsingException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		return null;
	}

	private static PartiallyLoadableMap parseMapXML(MapLoadingContext context) {
		MapTexturePack texturePack = MapTexturePackLoader.buildTexturePack(context);
		context.texturePack = texturePack;
		MapTile[][] tileMap = TileMapLoader.loadTileMap(context);
		return new PartiallyLoadableMap(tileMap, texturePack);
	}

}
