package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class RubbleStep4Soil extends Soil {

	public RubbleStep4Soil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.RUBBLE_STEP4, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
