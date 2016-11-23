package orre.gui.baseNodes;

import orre.geom.Shapes;
import orre.gl.RenderUtils;
import orre.gl.renderer.RenderState;
import orre.gl.vao.GeometryNode;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class GUIRootNode extends ContainerNode implements SceneNode {
	public GUIRootNode() {
		super("GUI root");
	}
	
	@Override
	public void preRender(RenderState state) {
		state.transformations.pushMatrix();
	}
	
	@Override
	public void postRender(RenderState state) {
		state.transformations.popMatrix();
	}
}
