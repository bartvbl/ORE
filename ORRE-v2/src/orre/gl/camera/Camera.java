package orre.gl.camera;

import org.lwjgl.util.vector.Matrix4f;

import orre.gl.renderer.RenderState;
import orre.sceneGraph.ContainerNode;

public class Camera extends ContainerNode
{
	protected final Matrix4f transformationMatrix = new Matrix4f();
	
	public Camera(String name) {
		super(name);
	}

	@Override
	public void preRender(RenderState state) {
		state.transformations.pushMatrix();
		transform(state);
	}
	
	protected void transform(RenderState state) {
		state.transformations.setViewMatrix(transformationMatrix);
	}
	
	@Override
	public void postRender(RenderState state) {
		state.transformations.popMatrix();
	}

	@Override
	public String toString() {
		return name;
	}
}
