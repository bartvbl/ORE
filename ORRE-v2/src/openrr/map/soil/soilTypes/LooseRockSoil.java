package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class LooseRockSoil extends Soil{

	public LooseRockSoil(SoilType soilType, SoilTextureSet textureSet, int[] rgb) {
		super(soilType, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles, ResourceCache cache) {
		
	}
}
