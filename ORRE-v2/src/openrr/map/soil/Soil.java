package openrr.map.soil;

import orre.entity.Entity;

public abstract class Soil {

	public final SoilType soilType;
	public final int[] rgb;
	public final SoilTextureSet textureSet;

	public Soil(SoilType soilType, SoilTextureSet textureSet, int[] rgb) {
		this.soilType = soilType;
		this.textureSet = textureSet;
		this.rgb = rgb;
	}

	public abstract void handleEntityTouch(Entity entity);

}
