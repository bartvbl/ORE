package orre.sceneGraph;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import orre.gl.util.TransformMatrixUtils;

public class Camera extends LeafNode
{
	protected final Matrix4f transformationMatrix = new Matrix4f();
	
	@Override
	public void preRender() {
		transform();
	}
	
	private void transform() {
		transformationMatrix.setIdentity();
	}

	@Override
	public String toString() {
		return "Camera";
	}
}
