package openrr.map.loader;

import java.util.zip.ZipFile;

import openrr.map.soil.SoilType;

import orre.geom.Dimension2D;

import nu.xom.Document;
import nu.xom.Element;

public class MapLoadingContext {

	public final Document mapXML;
	public final ZipFile mapFile;
	public final Element mapXMLRootElement;
	public Dimension2D mapSize;
	public boolean[][] wallMap;
	public SoilLibrary soilLibrary;
	public SoilType[][] soilMap;
	public double[][] heightMap;
	public MapTexturePack texturePack;

	public MapLoadingContext(Document mapXML, ZipFile mapFile) {
		this.mapXML = mapXML;
		this.mapFile = mapFile;
		this.mapXMLRootElement = mapXML.getRootElement();
	}

}
