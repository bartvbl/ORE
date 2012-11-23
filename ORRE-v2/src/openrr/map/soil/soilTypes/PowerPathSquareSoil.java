package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class PowerPathSquareSoil extends Soil {

	public PowerPathSquareSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.POWER_PATH_SQUARE, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
