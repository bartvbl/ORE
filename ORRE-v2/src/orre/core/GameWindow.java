package orre.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

public class GameWindow {
	public static final String WINDOW_TITLE = "Open Rock Raiders - Delta";
	public static final int DEFAULT_WINDOW_WIDTH = 1024;
	public static final int DEFAULT_WINDOW_HEIGHT = 768;
	
	public static void create()
	{
		try
		{
			doInitialization();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void destroy()
	{
		Display.destroy();
	}
	
	private static void doInitialization() throws LWJGLException
	{
		createDisplay();
		initOpenGL();
	}
	private static void createDisplay() throws LWJGLException
	{
		Display.setLocation(100, 100);
		Display.setDisplayMode(new DisplayMode(GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT));
		Display.setResizable(true);
		Display.setTitle(WINDOW_TITLE);
		Display.setIcon(new ByteBuffer[]{TextureLoader.getImageData("res/icon.png"), TextureLoader.getImageData("res/icon.png")});
		System.out.println("-- OpenRR v0.01 (java " + System.getProperty("java.version") + " running on " + System.getProperty("os.name") + " (" + System.getProperty("os.version") + ", "+System.getProperty("os.arch")+")) --");
		Display.create();
	}
	private static void initOpenGL()
	{
		glViewport(0, 0, GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(60.0f, (float)(GameWindow.DEFAULT_WINDOW_WIDTH/GameWindow.DEFAULT_WINDOW_HEIGHT), 0.1f, 10000.0f);
		glMatrixMode(GL_MODELVIEW);
		glClearColor(94.0f/255.0f, 161.0f/255.0f, 255.0f/255.0f, 0.5f);
		glClearDepth(1.0);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LIGHTING);
	}
}
