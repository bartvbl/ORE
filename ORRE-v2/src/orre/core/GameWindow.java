package orre.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import orre.gl.Texture;

public class GameWindow {
	public static final String WINDOW_TITLE = "Open Rock Raiders - Delta";
	public static final int DEFAULT_WINDOW_WIDTH = 1024;
	public static final int DEFAULT_WINDOW_HEIGHT = 768;
	
	private JFrame jframe;
	private Canvas canvas;
	
	private AtomicReference<Dimension> canvasSize = new AtomicReference<Dimension>();
	
	public GameWindow()
	{
		
	}
	public void create()
	{
		try
		{
			this.doInitialization();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	private void doInitialization() throws LWJGLException
	{
		this.createJFrame();
		this.makeWindowResizable();
		this.createDisplay();
		this.setIcon();
		this.initOpenGL();
	}
	public void resize()
	{
		Dimension dim = this.canvas.getSize();
		canvasSize.set(dim);
		dim = null;
	}
	private void createJFrame()
	{
		Canvas canvas = new Canvas();
		JFrame frame = new JFrame(GameWindow.WINDOW_TITLE);
		this.canvas = canvas;
		this.jframe = frame;
		frame.setSize(GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(canvas);
		frame.setVisible(true);
	}
	private void makeWindowResizable()
	{
		ComponentAdapter adapter = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resize();
			}
		};
		this.canvas.addComponentListener(adapter);
		this.canvas.setIgnoreRepaint(true);
	}
	private void createDisplay() throws LWJGLException
	{
		Display.setLocation(100, 100);
		Display.setTitle("Defence of the Creepers");
		Display.setDisplayMode(new DisplayMode(GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT));
		Display.setParent(canvas);
		Display.create();
	}
	private void setIcon()
	{
		Texture icon = new Texture("res/icon.png");
		this.jframe.setIconImage(icon.buffer);
	}
	private void initOpenGL()
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
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_CULL_FACE); 
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER,0.0f);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT1); 
		glEnable(GL_CULL_FACE);	
	}
}
