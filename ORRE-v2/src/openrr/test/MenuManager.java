package openrr.test;

import java.util.ArrayList;

import orre.core.GameWindow;

import openrr.test.Container;

public class MenuManager {
	
	ArrayList<Container> menus = new ArrayList<Container>();
	
	public MenuManager() {
		Button button = new Button(100,100, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT},"left");
		button.loadImages("res/images/menus/main/", "raider.bmp", "res/images/hover.png");
		Button button2 = new Button(100,140, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT},"left");
		button2.loadImages("res/images/menus/main/", "building.bmp", "res/images/hover.png");
		Container container = new Container(100,100,40,80);
		container.addChild(button);
		container.addChild(button2);
		menus.add(container);
	}
	
	public void draw () {
		for (Container menu : menus) {
			for (Button button : menu.items) {
				button.draw();
			}
		}
	}
	
	public boolean mouseIsInMenuBounds(int x, int y) {
		for (Container menu : menus) {
			if (menu.inBounds(x,y)) {
				return true;
			}
		}
		return false;
	}
	
	public Button getButtonInBounds(int x, int y) {
		for (Container menu : menus) {
			if (menu.inBounds(x,y)) {
				return menu.getButtonInBounds(x, y);
			}
		}
		return null;
	}


}
