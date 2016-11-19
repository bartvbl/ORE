package orre.gl.camera;

import org.lwjgl.util.vector.Matrix4f;
import orre.rendering.RenderState;
import orre.sceneGraph.ContainerNode;

public class Camera extends ContainerNode
{
	protected final Matrix4f transformationMatrix = new Matrix4f();
	
	public Camera(String name) {
		super(name);
	}

	@Override
	public void preRender(RenderState state) {
		transform(state);
	}
	
	protected void transform(RenderState state) {
		state.transformations.setViewMatrix(transformationMatrix);
	}

	@Override
	public String toString() {
		return name;
	}
}
