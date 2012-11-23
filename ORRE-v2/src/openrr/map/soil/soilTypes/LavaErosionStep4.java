package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class LavaErosionStep4 extends Soil {

	public LavaErosionStep4(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.LAVA_EROSION_STEP4, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles,ResourceCache cache) {
		
	}

}
