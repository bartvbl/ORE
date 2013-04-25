package orre.sceneGraph;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import orre.gl.util.TransformMatrixUtils;

public class Camera
{
	private final Matrix4f transformationMatrix = new Matrix4f();
	private static final Vector3f xAxis = new Vector3f(1, 0, 0);
	private static final Vector3f yAxis = new Vector3f(0, 1, 0);
	private static final Vector3f zAxis = new Vector3f(0, 0, 1);
	
	public void rotate(float rotationX, float rotationY, float rotationZ) {
		transformationMatrix.rotate(rotationZ, zAxis);
		transformationMatrix.rotate(rotationY, yAxis);
		transformationMatrix.rotate(rotationX, xAxis);
	}
	
	public void translate(float x, float y, float z) {
		transformationMatrix.translate(new Vector3f(x, y, z));
	}
	
	public void transform() {
		Matrix4f inverse = new Matrix4f();
		Matrix4f.invert(transformationMatrix, inverse);
		TransformMatrixUtils.applyMatrixOnCurrentMatrix(inverse);
	}
}
