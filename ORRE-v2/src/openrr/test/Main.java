package openrr.test;

import orre.resources.loaders.TextureLoader;
import orre.util.XMLDocument;
import org.dom4j.Node;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


import java.awt.Font;
import java.util.List;

import orre.core.GameWindow;
import orre.gl.texture.Texture;
import openrr.test.Button;
import openrr.test.MouseEvent;
import java.util.ArrayList;
import openrr.test.UI;

public class Main {

	static UI ui;
	
	public static void main (String args[]) {
		
		
		try {
			Display.setDisplayMode(new DisplayMode(GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glClearColor(94.0f/255.0f, 161.0f/255.0f, 255.0f/255.0f, 0.5f);	
		gluOrtho2D(0f, 640f, 0f, 480f);		
		glEnable (GL_BLEND);		
		glDepthFunc(GL_NEVER);		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		MenuManager menuManager = new MenuManager(new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT});
		ui = new UI(menuManager);
		/*XMLDocument doc = new XMLDocument("res/defaultGUI.xml");
		List<Node> menuNodes = doc.document.selectNodes("/ORRDefaultGUI/menus/*");
		Node containerNode = menuNodes.get(0).selectNodes("["+0+"]");*/
		
		TextButton but = new TextButton(100,100, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT}, "left","FIRE");
		
		while(!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			glEnable(GL_TEXTURE_2D);
			set2DMode(GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT);
				
			if (Mouse.next()==true) {
				if (Mouse.getEventButton()!=-1) {
					if (Mouse.getEventButton()==0) {
						if (Mouse.isButtonDown(0)) {
							ui.handleUI(new MouseEvent(Mouse.getX(), Mouse.getY(), MouseEvent.PRESS));
						}
						else {
							ui.handleUI(new MouseEvent(Mouse.getX(), Mouse.getY(), MouseEvent.RELEASE));
						}
					}
				}
				else {
					ui.handleUI(new MouseEvent(Mouse.getX(), Mouse.getY(), MouseEvent.MOVE));
				}
			}
			menuManager.draw();
			Display.update();
			Display.sync(50);
			but.draw();
		}
		
	}
	
	private static void set2DMode(int width, int height)
	{
		glDisable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, width, 0, height);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

}
