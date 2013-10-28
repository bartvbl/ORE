package orre.gl.renderer;

import java.util.ArrayList;

import orre.sceneGraph.SceneNode;
import orre.util.Stack;

public class RenderPass {
	public static void render(SceneNode rootNode) {
		Stack<SceneNode> nodeQueue = new Stack<SceneNode>();
		nodeQueue.push(rootNode);
		while(!nodeQueue.isEmpty()) {
			SceneNode currentNode = nodeQueue.pop();
			
			currentNode.preRender();
			currentNode.render();
			
			ArrayList<SceneNode> children = currentNode.getChildren();
			for(SceneNode child : children) {
				nodeQueue.push(child);
			}
			currentNode.postRender();
		}
	}
}
