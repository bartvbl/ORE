package orre.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

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
		initOpenGL();
	}
	private static void createDisplay(String gameName) throws Exception
	{
		Display.setLocation(100, 100);
		Display.setDisplayMode(new DisplayMode(GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT));
		Display.setResizable(true);
		Display.setTitle(gameName);
		Display.setIcon(new ByteBuffer[]{TextureLoader.getImageData("res/icon.png"), TextureLoader.getImageData("res/icon.png")});
		System.out.println("-- \""+gameName+"\" on ORRE v0.01 (java " + System.getProperty("java.version") + " running on " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ", "+System.getProperty("os.arch")+")) --");
		Display.create();
	}
	private static void initOpenGL()
	{
		glViewport(0, 0, GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60.0f, (float)(GameWindow.DEFAULT_WINDOW_WIDTH/GameWindow.DEFAULT_WINDOW_HEIGHT), 0.1f, 10000.0f);
		glMatrixMode(GL_MODELVIEW);
		glClearColor(0, 0, 0, 1);
		glClearDepth(1.0);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LIGHTING);
	}
}
