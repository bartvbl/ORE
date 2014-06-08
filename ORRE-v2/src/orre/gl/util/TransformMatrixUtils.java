package orre.gl.util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import static org.lwjgl.opengl.GL11.*;

public class TransformMatrixUtils {
	private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16 * 4);
	
	public static Matrix4f getModelViewMatrix() {
		return getMatrix(GL_MODELVIEW_MATRIX);
	}
	
	public static Matrix4f getProjectionMatrix() {
		return getMatrix(GL_PROJECTION_MATRIX);
	}
	
	public static void setCurrentTransformationMatrix(Matrix4f matrix) {
		matrixBuffer.rewind();
		matrix.store(matrixBuffer);
		matrixBuffer.rewind();
		glLoadMatrix(matrixBuffer);
	}

	private static Matrix4f getMatrix(int matrixEnum) {
		Matrix4f matrix = new Matrix4f();

		matrixBuffer.rewind();
		glGetFloat(matrixEnum, matrixBuffer);
		matrixBuffer.rewind();

		matrix.load(matrixBuffer);
		return matrix;
	}

	public static void applyMatrixOnCurrentMatrix(Matrix4f inverse) {
		matrixBuffer.rewind();
		inverse.store(matrixBuffer);
		matrixBuffer.rewind();
		glMultMatrix(matrixBuffer);
	}
}
