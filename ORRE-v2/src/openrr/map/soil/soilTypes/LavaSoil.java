package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class LavaSoil extends Soil {

	public LavaSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.LAVA, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
