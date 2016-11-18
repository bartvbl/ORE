package orre.sceneGraph;

import org.lwjgl.util.vector.Matrix4f;
import orre.rendering.RenderState;

public class Camera extends LeafNode
{
	protected final Matrix4f transformationMatrix = new Matrix4f();
	
	@Override
	public void preRender(RenderState state) {
		transform(state);
	}
	
	protected void transform(RenderState state) {
		state.transformations.setViewMatrix(transformationMatrix);
	}

	@Override
	public String toString() {
		return "Camera";
	}
}
