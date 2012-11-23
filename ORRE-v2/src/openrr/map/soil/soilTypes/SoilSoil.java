package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;
//yo dawg..
public class SoilSoil extends Soil {

	public SoilSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.SOIL, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles,ResourceCache cache) {
		
	}

}
