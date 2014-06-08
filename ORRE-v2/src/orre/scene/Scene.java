package orre.scene;

import orre.sceneGraph.SceneNode;

public class Scene {
	private final SceneNode rootNode;
	private final SceneNode sceneContentsRoot;
	
	public Scene(SceneNode rootNode, SceneNode sceneContentsRoot)
	{
		this.rootNode = rootNode;
		this.sceneContentsRoot = sceneContentsRoot;
	}
	
	public void tick() {
		
	}
	
	public void render() {
		
	}
}
