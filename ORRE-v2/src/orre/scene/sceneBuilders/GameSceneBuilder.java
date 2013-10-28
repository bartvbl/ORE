package orre.scene.sceneBuilders;

import orre.resources.ResourceCache;
import orre.scene.Scene;
import orre.sceneGraph.ContainerNode;

public class GameSceneBuilder {
	public Scene buildGameScene(ResourceCache cache) {
		ContainerNode rootNode = new ContainerNode();
		ContainerNode mapContentsRoot = new ContainerNode();
		Scene scene = new Scene(rootNode, mapContentsRoot);
		
		rootNode.addChild(cache.getMap().createSceneNode());
		return scene;
	}
}
