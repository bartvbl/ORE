package openrr.map.soil.soilTypes;

import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class SoilCreator {
	public static Soil buildSoilInstance(SoilType type, SoilTextureSet textureSet, int[] rgb) {
		switch(type) {
		case DIRT:
			return new DirtSoil(textureSet, rgb);
		}
		return new DirtSoil(textureSet, rgb); 
	}
}
