package openrr.map;

import orre.entity.Entity;

public class MapTile {
	private boolean isExplored = false;
	private boolean isWall;
	private Soil soil;
	private int[][] tileHeight;
	
	public MapTile(boolean isWall, Soil soil, int[][] tileHeight) {
		this.isWall = isWall;
		this.soil = soil;
		this.tileHeight = tileHeight;
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
}
