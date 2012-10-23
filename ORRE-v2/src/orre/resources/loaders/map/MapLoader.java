package orre.resources.loaders.map;

import java.io.IOException;

import openrr.map.MapTile;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
import orre.resources.FileToLoad;

public class MapLoader {

	public static PartiallyLoadableMap loadMap(FileToLoad currentFile) {
		Document mapXML = openMapXML(currentFile.getPath());
		PartiallyLoadableMap map = parseMapXML(mapXML);
		return map;
	}
	
	private static Document openMapXML(String src) {
		Builder builder = new Builder();
		try {
			return builder.build(src);
		}
		catch (ValidityException e) { e.printStackTrace(); }
		catch (ParsingException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		return null;
	}

	private static PartiallyLoadableMap parseMapXML(Document mapXML) {
		Element rootElement = mapXML.getRootElement();
		MapTile[][] tileMap = TileMapLoader.loadTileMap(rootElement.getFirstChildElement("mapDefinition"));
		return null;
	}

}
