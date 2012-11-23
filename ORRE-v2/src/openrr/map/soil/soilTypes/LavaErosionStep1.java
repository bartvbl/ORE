package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class LavaErosionStep1 extends Soil { //Make a core class that they inherit from, overriding the method that defines which soil type is next.

	public LavaErosionStep1(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.LAVA_EROSION_STEP1, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles, ResourceCache cache) {
		
	}

}
