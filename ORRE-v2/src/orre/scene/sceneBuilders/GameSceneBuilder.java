package orre.scene.sceneBuilders;

import orre.resources.ResourceCache;
import orre.scene.Scene;
import orre.sceneGraph.EmptySceneNode;

public class GameSceneBuilder {
	public Scene buildGameScene(ResourceCache cache) {
		EmptySceneNode rootNode = new EmptySceneNode();
		EmptySceneNode mapContentsRoot = new EmptySceneNode();
		Scene scene = new Scene(rootNode, mapContentsRoot);
		
	//	rootNode.addChild(cache.getMap().createSceneNode());
		return scene;
	}
}
