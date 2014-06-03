package openrr.map.loader;

import java.io.File;
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
import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.util.XMLLoader;

public class MapLoader implements ResourceTypeLoader {
	
	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		return loadMap(source);
	}
	
	@Override
	public ResourceType getResourceType() {
		return ResourceType.map;
	}

	public static PartiallyLoadableMap loadMap(UnloadedResource resource) throws Exception {
		ZipFile mapFile = openMap(resource.location);
		Document mapXML = readMapXML(mapFile);
		MapLoadingContext context = new MapLoadingContext(mapXML, mapFile);
		PartiallyLoadableMap map = parseMapXML(context);
		return map;
	}
	
	private static ZipFile openMap(File src) throws IOException {
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
