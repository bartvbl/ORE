package orre.gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

import orre.gl.RenderUtils;
import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

public class DefaultLoadingScreen implements LoadingScreenDrawer {
	
	private Texture loadingScreen;
	private Texture loadingBar;
	
	public DefaultLoadingScreen()
	{
		this.loadingScreen = TextureLoader.loadTextureFromFile("res/images/loadingScreen/loadingScreen.png");
		this.loadingBar = TextureLoader.loadTextureFromFile("res/images/loadingScreen/loadingBar.png");
	}

	public void draw(double progress) {
		RenderUtils.set2DMode();
		glColor4f(1, 1, 1, 1);
		this.loadingScreen.blit(0, 0, Display.getWidth(), Display.getHeight());
		int x = (int)(0.223f*Display.getWidth());
		int y = (int)(0.046f*Display.getHeight());
		this.loadingBar.blit(x, y, (int)((Display.getWidth() - 2.02f*x) * progress), 12);
	}
	
}
