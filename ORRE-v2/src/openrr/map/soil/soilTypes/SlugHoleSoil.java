package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class SlugHoleSoil extends Soil {

	public SlugHoleSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.SLUG_HOLE, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
