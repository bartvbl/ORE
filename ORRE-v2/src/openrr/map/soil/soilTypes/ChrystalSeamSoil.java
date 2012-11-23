package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class ChrystalSeamSoil extends Soil {

	public ChrystalSeamSoil(SoilTextureSet textureSet,int[] rgb) {
		super(SoilType.CHRYSTAL_SEAM, textureSet, rgb);
		
	}

	public void handleEntityTouch(Entity entity) {
		
	}

}
