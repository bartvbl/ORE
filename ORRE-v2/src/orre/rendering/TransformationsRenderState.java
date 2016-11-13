package orre.rendering;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import orre.util.Stack;

public class TransformationsRenderState {
	private Stack<Matrix4f> matrixStack = new Stack<Matrix4f>();
	private Matrix4f projectionMatrix = new Matrix4f();
	
	public TransformationsRenderState() {
		matrixStack.push(new Matrix4f());
	}
	
	public void pushMatrix() {
		matrixStack.push(new Matrix4f(matrixStack.peek()));
	}
	
	public void popMatrix() {
		matrixStack.pop();
	}

	public Matrix4f peekMatrix() {
		return matrixStack.peek();
	}

	public void setMatrix(Matrix4f matrix) {
		matrixStack.set(matrix);
	}

	public void setProjectionMatrix(Matrix4f matrix) {
		this.projectionMatrix = matrix;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void scale(Vector3f vec) {
		Matrix4f current = matrixStack.peek();
		Matrix4f.scale(vec, current, current);
	}

	public void translate(Vector3f vec) {
		Matrix4f current = matrixStack.peek();
		Matrix4f.translate(vec, current, current);
	}
}
