package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class RubbleStep2Soil extends Soil {

	public RubbleStep2Soil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.RUBBLE_STEP2, textureSet, rgb);
	}

	public void handleEntityTouch(Entity entity) {
		
	}

	public void generateGeometry(int[][] tileHeight, MapTile[][] tiles,ResourceCache cache) {
		
	}

}
