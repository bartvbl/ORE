package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class OreSeamSoil extends Soil {

	public OreSeamSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.ORE_SEAM, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
