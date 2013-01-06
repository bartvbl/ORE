package openrr.map;

import openrr.map.soil.Soil;
import orre.entity.Entity;
import orre.resources.ResourceCache;

public class MapTile {
	public static final int TILE_WIDTH = 8;
	public static final int TILE_HEIGHT = 8;
	
	public final int[][] tileHeight;
	private boolean isExplored = false;
	private boolean isWall;
	private Soil soil;
	private boolean hasChanged = true;
	
	public MapTile(boolean isWall, Soil soil, int[][] tileHeight) {
		this.isWall = isWall;
		this.soil = soil;
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
	
	public Soil getSoil() {
		return this.soil;
	}
	public void setSoil(Soil soil) {
		this.soil = soil;
		this.hasChanged = true;
	}
	
	public boolean requiresRedraw() {
		boolean requiresRedraw = hasChanged;
		hasChanged = false;
		return requiresRedraw;
	}
}
