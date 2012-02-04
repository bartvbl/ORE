package orre.gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import orre.gl.RenderUtils;

public class DefaultLoadingScreen implements LoadingScreenDrawer {
	
	private int angle = 0;

	public void draw(double progress) {
		RenderUtils.set2DMode(Display.getWidth(), Display.getHeight());
		glColor4f(0, 0, 0, 1);
		glBegin(GL_QUADS);
		glVertex2f(0, 100);
		glVertex2f((float)(progress*Display.getWidth()), 100);
		glVertex2f((float)(progress*Display.getWidth()), 130);
		glVertex2f(0, 130);
		glEnd();
		
		angle-= 3;
		
		glTranslatef(100, 200, 0);
		glRotatef(angle, 0, 0, 1);
		
		glBegin(GL_QUADS);
		glVertex2f(-30, -30);
		glVertex2f(30, -30);
		glVertex2f(30, 30);
		glVertex2f(-30, 30);
		glEnd();
	}
	
}
