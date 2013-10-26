package orre.sceneGraph;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import orre.gl.util.TransformMatrixUtils;

public class Camera
{
	private final Matrix4f transformationMatrix = new Matrix4f();
	private static final Vector3f xAxis = new Vector3f(1, 0, 0);
	private static final Vector3f yAxis = new Vector3f(0, 1, 0);
	private static final Vector3f zAxis = new Vector3f(0, 0, 1);
	
	private int counter = 0;
	
	private static final Vector3f rotation = new Vector3f(0, 0, 0);
	
	public void setRotation(double rotationX, double rotationY, double rotationZ) {
		rotation.set((float)rotationX, (float)rotationY, (float)rotationZ);
	}
	
	public void translate(double x, double y, double z) {
		transformationMatrix.translate(new Vector3f((float) x, (float) y, (float) z));
	}
	
	public void transform() {
		transformationMatrix.setIdentity();
		
		transformationMatrix.translate(new Vector3f(0, 0, 30));
		
		transformationMatrix.rotate((float) Math.toRadians(rotation.z), zAxis);
		transformationMatrix.rotate((float) Math.toRadians(rotation.y), yAxis);
		transformationMatrix.rotate((float) Math.toRadians(rotation.x), xAxis);
		
		Matrix4f inverse = new Matrix4f();
		Matrix4f.invert(transformationMatrix, inverse);
		TransformMatrixUtils.applyMatrixOnCurrentMatrix(inverse);
	}
}
