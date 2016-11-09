package orre.gl.renderer;

import java.util.ArrayList;

import orre.sceneGraph.SceneNode;

public class RenderPass {
	public static void render(SceneNode node) {
		if(!node.isVisible()) {
			return;
		}
		node.preRender();
		node.render();
			
		ArrayList<SceneNode> children = node.getChildren();
		for(SceneNode child : children) {
			render(child);
		}
		node.postRender();
	}
}
