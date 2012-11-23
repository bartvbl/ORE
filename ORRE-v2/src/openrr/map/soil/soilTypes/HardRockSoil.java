package openrr.map.soil.soilTypes;

import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;
import orre.entity.Entity;
import orre.resources.ResourceCache;

public class HardRockSoil extends Soil{

	public HardRockSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.HARD_ROCK, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}

