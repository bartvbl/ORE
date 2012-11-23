package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class PowerPathCrossSoil extends Soil {

	public PowerPathCrossSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.POWER_PATH_CROSS, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
