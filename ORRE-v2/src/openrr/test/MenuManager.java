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
		List<Node> menuNodes = doc.document.selectNodes("/ORRDefaultGUI/menus/*");
		int j = 0;
		for (Node menuNode : menuNodes) {
			List<Node> containerNodes = menuNode.selectNodes(menuNode.getPath()+"/*");
			ArrayList<Container> containers = new ArrayList<Container>();
			int k = 0;
			for (Node containerNode : containerNodes) {
				List<Node> itemNodes = menuNode.selectNodes(containerNode.getPath()+"/*");
				System.out.println(containerNode.selectNodes(containerNode.getPath()+"/*").size());
				ArrayList<Button> buttons = new ArrayList<Button>();
				int i = 0;
				for (Node itemNode : itemNodes) {
					buttons.add(new Button(Integer.parseInt(itemNode.valueOf("@x")),Integer.parseInt(itemNode.valueOf("@y")), screenSize, itemNode.valueOf("@align")));
					buttons.get(i).loadImages(itemNode.valueOf("@path"), itemNode.valueOf("@fileName"), itemNode.valueOf("@hoverPath"));
					i++;
				}
				containers.add(new Container(Integer.parseInt(containerNode.valueOf("@x")), Integer.parseInt(containerNode.valueOf("@y")), 
						Integer.parseInt(containerNode.valueOf("@w")), Integer.parseInt(containerNode.valueOf("@h")), 
						screenSize, containerNode.valueOf("@align") ));
				containers.get(k).addChildren(buttons);
				k++;
			}
			menus.add(new Menu(containers, Integer.parseInt(menuNode.valueOf("@x")), Integer.parseInt(menuNode.valueOf("@y")),
					screenSize, menuNode.valueOf("@bg"), menuNode.valueOf("@align")));
			if (!menuNode.valueOf("@startState").equals("none")) {
				int state = Menu.CLOSED;
				if (menuNode.valueOf("@startState").equals("open")) {
					state = Menu.OPEN;
				}
				menus.get(j).setAnimationVals(state,Integer.parseInt(menuNode.valueOf("@animFrames")),Integer.parseInt(menuNode.valueOf("@animDist")));
			}
			j++;
		}
		activeMenus.add(menus.get(1));
		//for (Container con : menus.get(1).getContainers()) {
		//	System.out.println(con.items.size());
		//}
	}


}
