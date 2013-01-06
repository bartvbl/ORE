package orre.gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import orre.gl.RenderUtils;
import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;
import orre.resources.loaders.obj.OBJLoader;

public class DefaultLoadingScreen implements LoadingScreenDrawer {
	
	private Texture loadingScreen;
	private Texture loadingBar;
	
	private int rotation = 0;
	private Texture loadingIcon;
	
	public DefaultLoadingScreen()
	{
		this.loadingScreen = TextureLoader.loadTextureFromFile("res/images/loadingScreen/loadingScreen.png");
		this.loadingBar = TextureLoader.loadTextureFromFile("res/images/loadingScreen/loadingBar.png");
		this.loadingIcon = TextureLoader.loadTextureFromFile("res/images/loadingScreen/busy.png");
	}

	public void draw(double progress) {
		RenderUtils.set2DMode();
		glPushMatrix();
		glColor4f(1, 1, 1, 1);
		
		this.loadingScreen.blit(0, 0, Display.getWidth(), Display.getHeight());
		int x = (int)(0.223f*Display.getWidth());
		int y = (int)(0.046f*Display.getHeight());
		int width = (int)((Display.getWidth() - 2.02f*x) * progress);
		int height = (int)((float)Display.getHeight() / 60f);
		
		this.loadingBar.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(x,y);
		glTexCoord2f((float)progress,0);
		glVertex2f(x+width,y);
		glTexCoord2f((float)progress,1);
		glVertex2f(x+width,y+height);
		glTexCoord2f(0,1);
		glVertex2f(x,y+height);
		glEnd();
		
		this.loadingIcon.bind();
		rotation += 3;
		int size = 50;
		glTranslatef(80, 80, 0);
		glRotatef(rotation, 0, 0, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(-size, -size);
		glTexCoord2f(1, 0);
		glVertex2f(size, -size);
		glTexCoord2f(1, 1);
		glVertex2f(size, size);
		glTexCoord2f(0, 1);
		glVertex2f(-size, size);
		glEnd();
		glShadeModel(GL_SMOOTH);
		glPopMatrix();
	}
	
}
