package openrr.map.soil;

import orre.entity.Entity;

public class Soil {
	public final int[] rgb;
	public final SoilTextureSet textureSet;

	public Soil(SoilTextureSet textureSet, int[] rgb) {
		this.textureSet = textureSet;
		this.rgb = rgb;
	}
}
