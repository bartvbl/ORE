package orre.gui.baseNodes;

import orre.gl.RenderUtils;
import orre.rendering.RenderState;

import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class GUIRootNode extends ContainerNode implements SceneNode {
	public GUIRootNode() {
		super("GUI root");
	}
	
	@Override
	public void preRender(RenderState state) {
		state.transformations.pushMatrix();
		RenderUtils.loadIdentity(state);
		RenderUtils.set2DMode(state);
	}
	
	@Override
	public void postRender(RenderState state) {
		state.transformations.popMatrix();
	}
}
