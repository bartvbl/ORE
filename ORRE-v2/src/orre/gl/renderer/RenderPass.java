package orre.gl.renderer;

import java.util.ArrayList;

import orre.sceneGraph.SceneNode;

public class RenderPass {
	public static void render(SceneNode node) {
		ArrayList<SceneNode> children = node.getChildren();
		
		node.preRender();
		node.render();

		for(SceneNode child : children) {
			RenderPass.render(child);
		}
		node.postRender();
	}
}
