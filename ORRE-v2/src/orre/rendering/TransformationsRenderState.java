package orre.rendering;

import org.lwjgl.util.vector.Matrix4f;

import orre.util.Stack;

public class TransformationsRenderState {
Stack<Matrix4f> matrixStack = new Stack<Matrix4f>();
	
	public TransformationsRenderState() {
		matrixStack.push(new Matrix4f());
	}
	
	public void pushMatrix() {
		matrixStack.push(matrixStack.peek());
	}
	
	public void popMatrix() {
		matrixStack.pop();
	}

	public Matrix4f peekMatrix() {
		return matrixStack.peek();
	}
}