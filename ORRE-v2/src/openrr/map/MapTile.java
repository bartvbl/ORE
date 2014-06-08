package openrr.map;

import openrr.map.soil.Soil;
import openrr.map.soil.SoilType;
import orre.entity.Entity;
import orre.resources.ResourceCache;

public class MapTile {
	public static final int TILE_WIDTH = 8;
	public static final int TILE_HEIGHT = 8;
	
	public final double[][] tileHeight;
	private boolean isExplored = false;
	private boolean isWall;
	private SoilType soilType;
	private boolean hasChanged = true;
	
	public MapTile(boolean isWall, SoilType soil, double[][] tileHeight) {
		this.isWall = isWall;
		this.soilType = soil;
		this.tileHeight = tileHeight;
	}
	
	public void explore() {
		this.isExplored = true;
		this.hasChanged = true;
	}
	public boolean isExplored() {
		return this.isExplored;
	}
	
	public void digg() {
		this.isWall = false;
		this.hasChanged = true;
	}
	public boolean isWall() {
		return this.isWall;
	}
	
	public void handleEntityTouch(Entity entity) {
		
	}
	
	public SoilType getSoilType() {
		return this.soilType;
	}
}
