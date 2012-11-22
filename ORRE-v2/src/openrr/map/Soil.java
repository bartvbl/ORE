package openrr.map;

import openrr.map.soil.SoilType;
import orre.entity.Entity;
import orre.geom.vbo.GeometryBuffer;

public abstract class Soil {

	public final SoilType soilType;

	public Soil(SoilType soilType) {
		this.soilType = soilType;
	}

	public abstract void handleEntityTouch(Entity entity);
	public abstract GeometryBuffer generateGeometry(int[][] tileHeight);

}
