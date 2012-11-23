package openrr.map.soil.soilTypes;

import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;
import orre.entity.Entity;
import orre.resources.ResourceCache;

public class HardRockSoil extends Soil{

	public HardRockSoil(SoilType soilType, SoilTextureSet textureSet, int[] rgb) {
		super(soilType, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles, ResourceCache cache) {
		
	}
}

