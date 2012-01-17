package openrr.test;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Node;

import orre.core.GameWindow;
import orre.util.XMLDocument;

import openrr.test.Container;


public class MenuManager {
	
	ArrayList<Menu> menus = new ArrayList<Menu>();
	ArrayList<Menu> activeMenus = new ArrayList<Menu>();
	ArrayList<Menu> inactiveMenus = new ArrayList<Menu>();
	
	public MenuManager(int[] screenSize) {
		/*Button button = new Button(100,100, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT},"left");
		button.loadImages("res/images/menus/main/", "raider.bmp", "res/images/hover.png");
		Button button2 = new Button(100,140, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT},"left");
		button2.loadImages("res/images/menus/main/", "building.bmp", "res/images/hover.png");
		Container container = new Container(100,100,40,80, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT}, "left");
		container.addChild(button);
		container.addChild(button2);
		Button button3 = new Button(55,100, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT},"left");
		button3.loadImages("res/images/menus/main/", "svehicle.bmp", "res/images/hover.png");
		Container container2 = new Container(55,100,40,40, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT}, "left");
		container2.addChild(button3);
		menus.add(container);
		menus.add(container2);*/
		loadGUI(screenSize);
	}
	
	public void draw () {
		for (Menu menu : activeMenus) {
			menu.draw();
		}
	}
	
	public boolean mouseIsInMenuBounds(int x, int y) {
		for (Menu menu : activeMenus) {
			for (Container container : menu.getContainers()) {
				if (container.inBounds(x,y)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Button getButtonInBounds(int x, int y) {
		for (Menu menu : activeMenus) {
			for (Container container : menu.getContainers()) {
				if (container.inBounds(x,y)) {
					return container.getButtonInBounds(x, y);
				}
			}
		}
		return null;
	}
	
	public void changeMenuZ(Menu menu, float val) {
		menu.changeZ(val);
		//Sort menus
	}
	
	public void sortMenusByZ() {
		int i=0;
		while (i<activeMenus.size()) {
			int j = 0;
			for (int k=i+1; k<activeMenus.size(); k++) {
				if (activeMenus.get(i).getZ()>activeMenus.get(k).getZ()) {
					j=k;
				}
			}
			if (j!=0) {
				activeMenus.add(j+1, activeMenus.get(i));
				activeMenus.remove(i);
			}
			else {
				i++;
			}
		}
	}

	public void loadGUI(int[] screenSize) {
		XMLDocument doc = new XMLDocument("res/defaultGUI.xml");
		this.menus = MenuDefinitionFileLoader.getMenuNodes(doc, screenSize);
		this.activeMenus.add(menus.get(1));
	}


}
