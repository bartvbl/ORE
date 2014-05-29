package orre.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.lwjgl.opengl.Display;

import orre.core.GameWindow;

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
	
	public static void set3DMode()
	{
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f, ((float)Display.getWidth()/(float)Display.getHeight()), RenderUtils.NEAR_POINT, RenderUtils.FAR_POINT);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glEnable(GL_CULL_FACE);
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);
	}
	
	public static void set2DMode()
	{
		glDisable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, Display.getWidth(), 0, Display.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_LIGHTING);
		glDisable(GL_CULL_FACE); 
	}
	
	public static void initOpenGL(int windowWidth, int windowHeight)
	{
		glViewport(0, 0, GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60.0f, (float)windowWidth / (float)windowHeight, 0.1f, 10000.0f);
		glMatrixMode(GL_MODELVIEW);
		glClearColor(0, 0, 0, 1);
		glClearDepth(1.0);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LIGHTING);
	}

	public static void resetSettings() {
		glClearColor(94.0f/255.0f, 161.0f/255.0f, 255.0f/255.0f, 0.5f);
	}
}
