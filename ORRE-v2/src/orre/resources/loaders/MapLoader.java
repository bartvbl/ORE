package orre.resources.loaders;

import java.io.IOException;
import java.io.InputStream;
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
import orre.resources.loaders.map.MapLoadingContext;
import orre.resources.loaders.map.MapTexturePack;
import orre.resources.loaders.map.MapTexturePackLoader;
import orre.resources.loaders.map.PartiallyLoadableMap;
import orre.resources.loaders.map.TileMapLoader;
import orre.util.XMLLoader;

public class MapLoader {

	public static PartiallyLoadableMap loadMap(FileToLoad currentFile) throws Exception {
		String mapSrc = currentFile.getPath();
		ZipFile mapFile = openMap(mapSrc);
		Document mapXML = readMapXML(mapFile);
		MapLoadingContext context = new MapLoadingContext(mapXML, mapFile);
		PartiallyLoadableMap map = parseMapXML(context);
		return map;
	}
	
	private static ZipFile openMap(String src) throws IOException {
		ZipFile mapFile = new ZipFile(src);
		return mapFile;
	} 
	
	private static Document readMapXML(ZipFile mapFile) throws IOException {
		ZipEntry mainMapXMLEntry = mapFile.getEntry("map.xml");
		InputStream inputStream = mapFile.getInputStream(mainMapXMLEntry);
		return XMLLoader.readXML(inputStream);
	}

	private static PartiallyLoadableMap parseMapXML(MapLoadingContext context) throws Exception {
		MapTexturePack texturePack = MapTexturePackLoader.buildTexturePack(context);
		context.texturePack = texturePack;
		MapTile[][] tileMap = TileMapLoader.loadTileMap(context);
		return new PartiallyLoadableMap(tileMap, texturePack);
	}

}
