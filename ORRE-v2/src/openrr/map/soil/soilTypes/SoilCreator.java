package openrr.map.soil.soilTypes;

import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;

public class SoilCreator {
	public static Soil buildSoilInstance(SoilType type, SoilTextureSet textureSet, int[] rgb) {
		switch(type) {
		case CHRYSTAL_SEAM:
			return new ChrystalSeamSoil(textureSet, rgb);
		case DIRT:
			return new DirtSoil(textureSet, rgb);
		case HARD_ROCK:
			return new HardRockSoil(textureSet, rgb);
		case LAVA:
			return new LavaSoil(textureSet, rgb);
		case LAVA_EROSION_STEP1:
			return new LavaErosionStep1(textureSet, rgb);
		case LAVA_EROSION_STEP2:
			return new LavaErosionStep2(textureSet, rgb);
		case LAVA_EROSION_STEP3:
			return new LavaErosionStep3(textureSet, rgb);
		case LAVA_EROSION_STEP4:
			return new LavaErosionStep4(textureSet, rgb);
		case LOOSE_ROCK:
			return new LooseRockSoil(textureSet, rgb);
		case ORE_SEAM:
			return new OreSeamSoil(textureSet, rgb);
		case POWER_PATH_CORNER:
			return new PowerPathCornerSoil(textureSet, rgb);
		case POWER_PATH_CROSS:
			return new PowerPathCrossSoil(textureSet, rgb);
		case POWER_PATH_END:
			return new PowerPathEndSoil(textureSet, rgb);
		case POWER_PATH_SQUARE:
			return new PowerPathSquareSoil(textureSet, rgb);
		case POWER_PATH_STRAIGHT:
			return new PowerPathStraightSoil(textureSet, rgb);
		case POWER_PATH_TINTERSECTION:
			return new PowerPathTIntersectionSoil(textureSet, rgb);
		case RECHARGE_SEAM:
			return new RechargeSeamSoil(textureSet, rgb);
		case RUBBLE_STEP1:
			return new RubbleStep1Soil(textureSet, rgb);
		case RUBBLE_STEP2:
			return new RubbleStep2Soil(textureSet, rgb);
		case RUBBLE_STEP3:
			return new RubbleStep3Soil(textureSet, rgb);
		case RUBBLE_STEP4:
			return new RubbleStep4Soil(textureSet, rgb);
		case SLUG_HOLE:
			return new SlugHoleSoil(textureSet, rgb);
		case SOIL:
			return new SoilSoil(textureSet, rgb);
		case SOLID_ROCK:
			return new SolidRockSoil(textureSet, rgb);
		case WATER:
			return new WaterSoil(textureSet, rgb);
		}
		return new DirtSoil(textureSet, rgb); 
	}
}
