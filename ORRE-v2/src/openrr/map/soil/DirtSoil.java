package openrr.map.soil;

import orre.entity.Entity;
import orre.geom.vbo.GeometryBuffer;
import openrr.map.Soil;

public class DirtSoil extends Soil{
	public DirtSoil() {
		super(SoilType.DIRT);
	}
	
	public void handleEntityTouch(Entity entity) {}

	public GeometryBuffer generateGeometry(int[][] tileHeight) {
		return null;
	}
}
