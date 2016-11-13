package orre.geom;

import org.lwjgl.util.vector.Matrix4f;

public class Projections {
    private static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }
     
    private static float degreesToRadians(float degrees) {
        return degrees * (float)(Math.PI / 180d);
    }
	
	public static Matrix4f createPerspectiveMatrix(float aspectRatio, float fov, float nearPlane, float farPlane) {
		Matrix4f projectionMatrix = new Matrix4f();
		 
		float y_scale = coTangent(degreesToRadians(fov / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = farPlane - nearPlane;
		 
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((farPlane + nearPlane) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * nearPlane * farPlane) / frustum_length);
		projectionMatrix.m33 = 0;
		
		return projectionMatrix;
	}
	
	public static Matrix4f createOrthoMatrix(float left, float right, float bottom, float top, float near, float far) {
		Matrix4f m = new Matrix4f();
		
		float x_orth = 2 / (right - left);
		float y_orth = 2 / (top - bottom);
		float z_orth = -2 / (far - near);

		float tx = -(right + left) / (right - left);
		float ty = -(top + bottom) / (top - bottom);
		float tz = -(far + near) / (far - near);

		m.m00 = x_orth;
		m.m10 = 0;
		m.m20 = 0;
		m.m30 = 0;
		m.m01 = 0;
		m.m11 = y_orth;
		m.m21 = 0;
		m.m31 = 0;
		m.m02 = 0;
		m.m12 = 0;
		m.m22 = z_orth;
		m.m32 = 0;
		m.m03 = tx;
		m.m13 = ty;
		m.m23 = tz;
		m.m33 = 1;
		
		return m;
	}
}
