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
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				Chunk chunk = chunks[i][j];
				chunk.setResourceCache(cache);
				mainSceneNode.addChild(chunk);
			}
		}
		return mainSceneNode;
	}
}
