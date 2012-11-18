package openrr.map;

public class Chunk {
	private final MapTile[][] tiles;
	private boolean requiresRebuild = true;

	public Chunk(MapTile[][] tiles) {
		this.tiles = tiles;
	}
	
	public void updateTile(){}
	
	public boolean requiresRebuild() {
		return requiresRebuild;
	}
	
	public void rebuild() {
		
	}
}
