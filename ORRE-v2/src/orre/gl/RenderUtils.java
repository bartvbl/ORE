package orre.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;

import orre.core.GameWindow;
import orre.geom.Projections;
import orre.rendering.RenderState;

public class RenderUtils {
	public static final float NEAR_POINT = 0.1f;
	public static final float FAR_POINT = 250f;
	
	public static void newFrame()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glViewport(0, 0, Display.getWidth(), Display.getHeight()); 
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
	}
	
	public static void set3DMode(RenderState state)
	{
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		
		Matrix4f projection = Projections.createPerspectiveMatrix((float)Display.getWidth()/(float)Display.getHeight(), 90, RenderUtils.NEAR_POINT, RenderUtils.FAR_POINT);
		state.transformations.setMatrix(projection);
		
		glEnable(GL_CULL_FACE);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void set2DMode(RenderState state)
	{
		glDisable(GL_DEPTH_TEST);
		Matrix4f orthoMatrix = Projections.createOrthoMatrix(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		state.transformations.setProjectionMatrix(orthoMatrix);
		glDisable(GL_CULL_FACE); 
	}
	
	public static void initOpenGL(int windowWidth, int windowHeight)
	{
		glViewport(0, 0, GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT);
		
		glClearColor(0, 0, 0, 1);
		glClearDepth(1.0);
		
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
	}

	public static void resetSettings() {
		glClearColor(94.0f/255.0f, 161.0f/255.0f, 255.0f/255.0f, 1f);
	}

	public static void loadIdentity(RenderState state) {
		state.transformations.setMatrix(new Matrix4f());
	}
}
