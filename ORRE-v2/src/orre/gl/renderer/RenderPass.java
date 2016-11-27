package orre.gl.renderer;

import java.util.ArrayList;

import orre.gl.vao.GeometryNode;
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

	public static void renderSingleNode(SceneNode node, RenderState state) {
		node.preRender(state);
		node.render(state);
		node.postRender(state);
	}
}
