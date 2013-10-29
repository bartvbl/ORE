package orre.gl.renderer;

import java.util.ArrayList;

import orre.sceneGraph.SceneNode;
import orre.util.Stack;

public class RenderPass {
	public static void render(SceneNode node) {
		node.preRender();
		node.render();
			
		ArrayList<SceneNode> children = node.getChildren();
		for(SceneNode child : children) {
			render(child);
		}
		node.postRender();
	}
}
