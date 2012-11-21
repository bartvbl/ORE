package openrr.map;

import orre.sceneGraph.SceneNode;

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

	public SceneNode getSceneNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
