package openrr.test;

import openrr.test.Button;

import java.util.ArrayList;

public class Container {

	private int x;
	private int y;
	private int xOffset;
	private int yOffset;
	private int width;
	private int height;
	
	private int[] screenDims;
	
	private String align;
	
	ArrayList<Button> items = new ArrayList<Button>();
	
	public Container(int x, int y, int w, int h, int[] screenSize, String inAlign) { 
		xOffset = x;
		yOffset = y;
		width = w;
		height = h;
		align = inAlign;
		screenDims = screenSize;
		setPosition();
	}
	
	public void addChild(Button button) {
		button.addParent(this);
		items.add(button);
	}
	
	public void addChildren(ArrayList<Button> buttons) {
		for (Button button : buttons) {
			button.addParent(this);
		}
		items.addAll(buttons);
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
	
	public void setPosition() {
		if (yOffset>0) {
			y = yOffset;
		}
		else {
			y = screenDims[1]+yOffset;
		}
		
		if (align.equals("left")) {
			x = xOffset;
		}
		else {
			if (align.equals("right")) {
				x = screenDims[0]+xOffset;
			}
			else {
				x = (screenDims[0]/2)+(width/2);
			}
		}
	}
	
	public int getX() {
		return xOffset;
	}
	
	public void changeX(int xShift) {
		xOffset = xOffset+xShift;
		setPosition();
	}

	public void changeY(int yShift) {
		yOffset = yOffset+yShift;
		setPosition();
	}
	
}
