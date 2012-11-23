package openrr.map;

import orre.resources.ResourceCache;
import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class ChunkCache {
	private final Chunk[][] chunks;

	public ChunkCache(Chunk[][] chunks) {
		this.chunks = chunks;
	}

	public SceneNode createSceneNode(ResourceCache cache) {
		SceneNode mainSceneNode = new EmptySceneNode();
		for(int i = 0; i < chunks.length; i++) {
			for(int j = 0; j < chunks[0].length; j++) {
				Chunk chunk = chunks[i][j];
				chunk.setResourceCache(cache);
				mainSceneNode.addChild(chunk);
			}
		}
		return mainSceneNode;
	}
	
	public void update() {
		for(int i = 0; i < chunks.length; i++) {
			for(int j = 0; j < chunks[0].length; j++) {
				if(chunks[i][j].requiresRebuild()) {
					chunks[i][j].rebuild();
				}
			}
		}
	}
}
