package orre.gl.camera;

import orre.gl.RenderUtils;
import orre.rendering.RenderState;

public class OrthographicCameraNode extends Camera {

	public OrthographicCameraNode() {
		super("Default Orthographic Camera");
		
	}
	

	
	protected void transform(RenderState state) {
		RenderUtils.set2DMode(state);
		RenderUtils.loadIdentity(state);
	}

}
