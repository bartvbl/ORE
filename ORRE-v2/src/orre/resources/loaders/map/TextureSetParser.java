package orre.resources.loaders.map;

import nu.xom.Element;
import openrr.map.WallType;
import openrr.map.soil.SoilTextureCoordinateSet;

public class TextureSetParser {

	public static void parseTextureSet(SoilTextureCoordinateSet textureSet, Element textureSetElement) {
		/*<soilTextureSet type="baseTile" r="0" g="0" b="0">
			<texture type="ground" texture="terrain" x="0" y="0" />
			<texture type="wall" texture="terrain" x="5" y="0" />
			<texture type="wallCorner" texture="terrain" x="5" y="1" />
			<texture type="invertedCorner" texture="terrain" x="5" y="2" />
			<texture type="enforced" texture="terrain" x="5" y="3" />
			<texture type="roof" texture="terrain" x="0" y="1" />
			<texture type="crevice" texture="terrain" x="1" y="3" />
		</soilTextureSet>*/
		WallType[] wallTypes = WallType.values();
		for(WallType wallType : wallTypes) {
			
		}
		
	}

}
