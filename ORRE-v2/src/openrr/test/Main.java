package openrr.test;

import orre.util.XMLDocument;
import org.dom4j.Node;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import org.lwjgl.input.Mouse;

import java.util.List;

import orre.core.GameWindow;
import orre.gl.Texture;
import openrr.test.Button;
import openrr.test.MouseEvent;
import java.util.ArrayList;
import openrr.test.UI;

public class Main {
	
	static XMLDocument doc = new XMLDocument("res/defaultGUI.xml");
	static ArrayList<Button> buttons = new ArrayList<Button>();
	
	static UI ui;
	
	public static void main (String args[]) {
		
		ui = new UI();
		
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
		loadGUI();
		Texture test = new Texture("res/images/menus/main/raider.bmp");
		//System.out.println(buttons.get(0).stateImages.get(0)+" "+buttons.get(0).stateImages.get(1)+buttons.get(0).stateImages.get(2)+buttons.get(0).stateImages.get(4));
		while(!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
			//glMatrixMode(GL_MODELVIEW);
			//glLoadIdentity();
			glEnable(GL_TEXTURE_2D);
			set2DMode(GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT);
			for (Button button : buttons) {
				button.draw();
				}
			if (Mouse.next()==true) {
				if (Mouse.getEventButton()!=-1) {
					if (Mouse.getEventButton()==1) {
						if (Mouse.isButtonDown(1)) {
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
			Display.update();
			Display.sync(50);
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
	
	
	public static void loadGUI() {
		List<Node> menuNodes = doc.document.selectNodes("/ORRDefaultGUI/menus/*");
		for (Node menuNode : menuNodes) {
			List<Node> itemNodes = menuNode.selectNodes(menuNode.getPath()+"/*");
			int i = 0;
			for (Node itemNode : itemNodes) {
				buttons.add(new Button(Integer.parseInt(itemNode.valueOf("@x")),Integer.parseInt(itemNode.valueOf("@y")), new int[] {800,600}, itemNode.valueOf("@align")));
				buttons.get(i).loadImages(itemNode.valueOf("@path"), itemNode.valueOf("@fileName"), itemNode.valueOf("@hoverPath"));
				i++;
			}
		}
	}
	
	
	public String searchNode(String name) {
		String path;
		int nodeSize=doc.getNumChilds("/ORRDefaultSounds/sounds");
		for (int i=0; i<nodeSize; i++) {
			if (doc.getAttribute("/ORRDefaultSounds/sounds/sound["+(i+1)+"]","name").equals(name)) {
				path = "src/res/sounds/"+doc.getAttribute("/ORRDefaultSounds/sounds/sound["+(i+1)+"]","src");
				return path;
			}
		}
		return "";
	}

}