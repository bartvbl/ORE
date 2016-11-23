package orre.lxf;

import orre.geom.Axis;
import orre.gl.renderer.RenderState;
import orre.gl.renderer.ShaderProperty;
import orre.sceneGraph.CoordinateNode;

public class LXFRootNode extends CoordinateNode {

	public LXFRootNode(String name) {
		super(name);
	}
	
	@Override
	public void preRender(RenderState state) {
		state.shaders.setPropertyb(ShaderProperty.TEXTURES_ENABLED, false);
		super.preRender(state);
	}
	
	@Override
	public void postRender(RenderState state) {
		state.shaders.setPropertyb(ShaderProperty.TEXTURES_ENABLED, true);
		super.postRender(state);
	}

}
