package orre.gl.util;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW_MATRIX;
import static org.lwjgl.opengl.GL11.GL_PROJECTION_MATRIX;
import static org.lwjgl.opengl.GL11.GL_VIEWPORT;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glLoadMatrix;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.util.glu.GLU.gluProject;
import static org.lwjgl.util.glu.GLU.gluUnProject;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class CoordConverter {
	private static final FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
	private static final FloatBuffer projection = BufferUtils.createFloatBuffer(16);
	private static final IntBuffer viewport = BufferUtils.createIntBuffer(16);
	private static final FloatBuffer location = BufferUtils.createFloatBuffer(3);
	private static final FloatBuffer winZ = BufferUtils.createFloatBuffer(1);
	
	public static float[] getScreenCoords(float x, float y, float z)
	{
		modelView.clear().rewind();
		projection.clear().rewind();
		viewport.clear().rewind();
		location.clear().rewind();
		
		glGetFloat(GL_MODELVIEW_MATRIX, modelView);
		glGetFloat(GL_PROJECTION_MATRIX, projection);
		glGetInteger(GL_VIEWPORT, viewport);
		
		gluProject(x, y, z, modelView, projection, viewport, location);
		return new float[] {location.get(0), location.get(1), location.get(2)};
	}
	public static float[] getMapCoords(int x, int y)
	{
		modelView.clear().rewind();
		projection.clear().rewind();
		viewport.clear().rewind();
		location.clear().rewind();
		winZ.clear().rewind();
		
		glGetFloat(GL_MODELVIEW_MATRIX, modelView);
		glGetFloat(GL_PROJECTION_MATRIX, projection);
		glGetInteger(GL_VIEWPORT, viewport);
		
		glReadPixels(x, y, 1, 1, GL_DEPTH_COMPONENT, GL_FLOAT, winZ);
		gluUnProject(x, y, winZ.get(0), modelView, projection, viewport, location);
		return new float[] {location.get(0), location.get(1), location.get(2)};
	}
}
