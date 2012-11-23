package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class RubbleStep1Soil extends Soil {

	public RubbleStep1Soil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.RUBBLE_STEP1, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
