package openrr.map;

import openrr.map.soil.SoilType;
import orre.entity.Entity;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.ResourceCache;

public abstract class Soil {

	public final SoilType soilType;

	public Soil(SoilType soilType) {
		this.soilType = soilType;
	}

	public abstract void handleEntityTouch(Entity entity);
	public abstract void generateGeometry(int[][] tileHeight, MapTile[][] tiles, ResourceCache cache);

}
