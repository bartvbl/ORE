package openrr.map;

import openrr.map.soil.Soil;
import orre.entity.Entity;
import orre.resources.ResourceCache;

public class MapTile {
	public static final int TILE_WIDTH = 8;
	public static final int TILE_HEIGHT = 8;
	
	private boolean isExplored = false;
	private boolean isWall;
	private Soil soil;
	private int[][] tileHeight;
	private int x;
	private int y;
	
	public MapTile(boolean isWall, Soil soil, int[][] tileHeight, int x, int y) {
		this.isWall = isWall;
		this.soil = soil;
		this.tileHeight = tileHeight;
		this.x = x;
		this.y = y;
	}
	
	public void explore() {
		this.isExplored = true;
	}
	
	public void digg() {
		this.isWall = false;
	}
	
	public boolean isWall() {
		return this.isWall;
	}
	
	public boolean isExplored() {
		return this.isExplored;
	}
	
	public void handleEntityTouch(Entity entity) {
		this.soil.handleEntityTouch(entity);
	}
	
	public Soil getSoil() {
		return this.soil;
	}
	
	public void setSoil(Soil soil) {
		this.soil = soil;
	}

	public void render(ResourceCache cache, MapTile[][] tileMap) {
		this.soil.generateGeometry(tileHeight, tileMap, x, y);
	}
}
