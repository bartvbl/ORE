package orre.gameWorld.core;

import orre.sceneGraph.SceneNode;
import orre.util.Stack;

public class GraphicsObject {
	public final GraphicsObjectType type;
	public final SceneNode rootNode;
	private final Stack<GameObject> ownership = new Stack<GameObject>();

	public GraphicsObject(GraphicsObjectType type, SceneNode sceneNode) {
		this.rootNode = sceneNode;
		this.type = type;
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
