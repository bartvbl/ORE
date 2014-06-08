package openrr.map.soil;

import orre.entity.Entity;

public class Soil {
	public final int[] rgb;
	public final SoilTextureCoordinateSet textureSet;

	public Soil(SoilTextureCoordinateSet textureSet, int[] rgb) {
		this.textureSet = textureSet;
		this.rgb = rgb;
	}
}
