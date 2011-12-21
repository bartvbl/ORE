package openrr.test;

import java.util.ArrayList;

import orre.core.GameWindow;

import openrr.test.Container;

public class MenuManager {
	
	ArrayList<Container> menus = ArrayList<Container>();
	
	public MenuManager() {
		Button button = new Button(100,100, new int[] {GameWindow.DEFAULT_WINDOW_WIDTH, GameWindow.DEFAULT_WINDOW_HEIGHT},"left");
		menus.add(new Container(100,100,40,40));
		
	}
	
	public void draw () {
		for (Container menu : menus) {
			for (Button button : menu.items) {
				button.draw();
			}
		}
	}

}
