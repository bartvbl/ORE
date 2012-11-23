package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class PowerPathTIntersectionSoil extends Soil {

	public PowerPathTIntersectionSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.POWER_PATH_TINTERSECTION, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles,ResourceCache cache) {
		
	}

}
