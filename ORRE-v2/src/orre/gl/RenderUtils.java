package orre.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.lwjgl.opengl.Display;

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
		//glEnable(GL_NORMALIZE);
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
//		glDisable(GL_CULL_FACE); 
	}
}
