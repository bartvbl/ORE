package openrr.test;

import openrr.test.Button;

import java.util.ArrayList;
import openrr.test.OutOfBoundsException;

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
	
	public int[] getBounds() {
		return new int[] {x, x+width, y, y+height};
	}
	
}
