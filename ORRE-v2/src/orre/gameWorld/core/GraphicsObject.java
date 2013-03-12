package orre.gameWorld.core;

import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;
import orre.util.Stack;

public class GraphicsObject {
	private SceneNode rootNode;
	private final Stack<GameObject> ownership = new Stack<GameObject>();

	public GraphicsObject() {
		this.rootNode = new EmptySceneNode();
	}
	
	public void takeOwnership(GameObject newOwner) {
		releaseCurrentOwner();
		newOwner.takeControl(this);
		ownership.push(newOwner);
	}
	
	public void returnOwnership() {
		releaseCurrentOwner();
		ownership.pop();
		if(!ownership.isEmpty()) {
			GameObject newOwner = ownership.peek();
			newOwner.takeControl(this);
		}
	}
	
	private void releaseCurrentOwner() {
		if(!ownership.isEmpty()) {
			GameObject previousOwner = ownership.peek();
			previousOwner.releaseControl(this);			
		}
	}
}
