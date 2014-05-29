package orre.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.io.File;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import orre.gl.RenderUtils;
import orre.resources.loaders.TextureLoader;

public class GameWindow {
	public static final int DEFAULT_WINDOW_WIDTH = 1024;
	public static final int DEFAULT_WINDOW_HEIGHT = 768;
	
	public static void create(String gameName)
	{
		try
		{
			doInitialization(gameName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void destroy()
	{
		Display.destroy();
	}
	
	private static void doInitialization(String gameName) throws Exception
	{
		createDisplay(gameName);
		RenderUtils.initOpenGL(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	}
	private static void createDisplay(String gameName) throws Exception
	{
		Display.setLocation(100, 100);
		Display.setDisplayMode(new DisplayMode(GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT));
		Display.setResizable(true);
		Display.setTitle(gameName);
		Display.setIcon(new ByteBuffer[]{TextureLoader.getImageData(new File("res/icon.png")), TextureLoader.getImageData(new File("res/icon.png"))});
		System.out.println("-- \""+gameName+"\" on ORRE v0.01 (java " + System.getProperty("java.version") + " running on " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ", "+System.getProperty("os.arch")+")) --");
		Display.create();
	}
}
