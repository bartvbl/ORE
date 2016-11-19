package orre.rendering;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import orre.util.Stack;

public class TransformationsRenderState {
	private Stack<Matrix4f> modelMatrixStack = new Stack<Matrix4f>();
	private Stack<Matrix4f> projectionMatrixStack = new Stack<Matrix4f>();
	private Stack<Matrix4f> viewMatrixStack = new Stack<Matrix4f>();
	
	public TransformationsRenderState() {
		modelMatrixStack.push(new Matrix4f());
		projectionMatrixStack.push(new Matrix4f());
		viewMatrixStack.push(new Matrix4f());
	}
	
	public void pushMatrix() {
		modelMatrixStack.push(new Matrix4f(modelMatrixStack.peek()));
		projectionMatrixStack.push(new Matrix4f(projectionMatrixStack.peek()));
		viewMatrixStack.push(new Matrix4f(viewMatrixStack.peek()));
	}
	
	public void popMatrix() {
		modelMatrixStack.pop();
		projectionMatrixStack.pop();
		viewMatrixStack.pop();
	}

	public Matrix4f peekMatrix() {
		return modelMatrixStack.peek();
	}

	public void setMatrix(Matrix4f matrix) {
		modelMatrixStack.set(matrix);
	}

	public void setProjectionMatrix(Matrix4f matrix) {
		this.projectionMatrixStack.set(matrix);
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrixStack.peek();
	}
	
	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrixStack.set(viewMatrix);
	}
	
	public Matrix4f getViewMatrix() {
		return this.viewMatrixStack.peek();
	}

	public void scale(Vector3f vec) {
		Matrix4f current = modelMatrixStack.peek();
		Matrix4f.scale(vec, current, current);
	}

	public void translate(Vector3f vec) {
		Matrix4f current = modelMatrixStack.peek();
		Matrix4f.translate(vec, current, current);
	}

	public void rotate(float rotation, Vector3f axis) {
		Matrix4f current = modelMatrixStack.peek();
		Matrix4f.rotate((float) Math.toRadians(rotation), axis, current, current);
	}

	public void translate(float x, float y, float z) {
		translate(new Vector3f(x, y, z));
	}

	public void scale(float x, float y, float z) {
		scale(new Vector3f(x, y, z));
	}

	public void applyTransformation(Matrix4f matrix) {
		Matrix4f current = modelMatrixStack.peek();
		Matrix4f.mul(current, matrix, current);
	}

	public void printState() {
		System.out.println("Projection stack:");
		System.out.println(projectionMatrixStack);
		System.out.println("View stack:");
		System.out.println(viewMatrixStack);
		System.out.println("Model stack:");
		System.out.println(modelMatrixStack);
	}
}
