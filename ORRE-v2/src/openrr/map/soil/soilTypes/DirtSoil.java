package openrr.map.soil.soilTypes;

import orre.entity.Entity;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceCache;
import openrr.map.MapTile;
import openrr.map.soil.Soil;
import openrr.map.soil.SoilTextureSet;
import openrr.map.soil.SoilType;
import static org.lwjgl.opengl.GL11.*;

public class DirtSoil extends Soil{

	public DirtSoil(SoilTextureSet textureSet, int[] rgb) {
		super(SoilType.DIRT, textureSet, rgb);
	}
	
	public void handleEntityTouch(Entity entity) {}
}
