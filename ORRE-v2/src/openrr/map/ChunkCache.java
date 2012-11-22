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
		for(Chunk chunkRow[] : chunks) {
			for(Chunk chunk : chunkRow) {
				mainSceneNode.addChild(chunk);
			}
		}
		return mainSceneNode;
	}
}
