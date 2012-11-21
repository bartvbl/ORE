package openrr.map;

import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class ChunkCache {
	private final Chunk[][] chunks;
	private final SceneNode mainSceneNode;

	public ChunkCache(Chunk[][] chunks) {
		this.chunks = chunks;
		this.mainSceneNode = new EmptySceneNode();
		for(Chunk chunkRow[] : chunks) {
			for(Chunk chunk : chunkRow) {
				this.mainSceneNode.addChild(chunk.getSceneNode());
				//no. better to make each Chunk render itself.
			}
		}
	}

	public SceneNode getSceneNode() {
		return mainSceneNode;
	}
	
	public void tick() {
		
	}
}
