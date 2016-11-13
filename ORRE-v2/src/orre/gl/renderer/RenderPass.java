package orre.gl.renderer;

import java.util.ArrayList;

import orre.rendering.RenderState;
import orre.sceneGraph.SceneNode;

public class RenderPass {
	public static void render(SceneNode node, RenderState state) {
		if(!node.isVisible()) {
			return;
		}
		node.preRender(state);
		node.render(state);
			
		ArrayList<SceneNode> children = node.getChildren();
		for(SceneNode child : children) {
			render(child, state);
		}
		node.postRender(state);
	}
}
