package openrr.map;

import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class Chunk extends EmptySceneNode implements SceneNode{
	private final MapTile[][] tiles;
	private boolean requiresRebuild = true;
	private final int x, y;

	public Chunk(MapTile[][] tiles, int x, int y) {
		this.tiles = tiles;
		this.x = x;
		this.y = y;
	}
	
	public void preRender() {
		if(requiresRebuild){
			rebuild();
		}
	}
	
	public void render() {
		
	}
	
	public boolean requiresRebuild() {
		return requiresRebuild;
	}
	
	public void rebuild() {
		
	}
}
