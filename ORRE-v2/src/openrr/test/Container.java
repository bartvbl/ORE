package openrr.test;

import openrr.test.Button;

import java.util.ArrayList;

public class Container {

	private int x;
	private int y;
	private int width;
	private int height;
	
	ArrayList<Button> items = new ArrayList<Button>();
	
	public Container(int inX, int inY, int w, int h) { 
		x = inX;
		y = inY;
		width = w;
		height = h;
	}
	
	public void addChild(Button button) {
		button.addParent(this);
		items.add(button);
	}
	
	public boolean inBounds(int cX, int cY) {
		return (x <= cX && cX <= x+width) && (y <= cY && cY <= y+height);
	}
	
	public Button getButtonInBounds(int cX, int cY) {
		for (Button item : items) {
			if (item.inBounds(cX, cY)) {
				return item;
			}
		}
		return null;
	}
	
}
