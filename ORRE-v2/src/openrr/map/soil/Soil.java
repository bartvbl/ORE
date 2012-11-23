package openrr.map.soil;

import openrr.map.MapTile;
import orre.entity.Entity;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.ResourceCache;

public abstract class Soil {

	public final SoilType soilType;
	private final SoilTextureSet textureSet;

	public Soil(SoilType soilType, SoilTextureSet textureSet) {
		this.soilType = soilType;
		this.textureSet = textureSet;
	}

	public abstract void handleEntityTouch(Entity entity);
	public abstract void generateGeometry(int[][] tileHeight, MapTile[][] tiles, ResourceCache cache);

}
