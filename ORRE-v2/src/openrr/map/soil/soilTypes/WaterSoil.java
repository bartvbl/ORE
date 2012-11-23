package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class WaterSoil extends Soil {

	public WaterSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.WATER, textureSet, rgb);
	}
	
	public void handleEntityTouch(Entity entity) {
		
	}

}
