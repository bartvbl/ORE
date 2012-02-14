package openrr.test;

import java.util.ArrayList;

import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

import java.io.FileNotFoundException;
import java.io.FileInputStream;

import org.lwjgl.util.Rectangle;

public class Button {
	
	public static int NORMAL = 0;
	public static int PRESSED = 1;
	public static int INACTIVE = 2;
	public static int HOVER = 3;
	
	protected int x;
	protected int y;
	protected int xOffset;
	protected int yOffset;
	protected int width;
	protected int height;
	
	protected int[] screenDims;
	
	protected int state;
	
	protected String align;
	
	protected Container parent;
	
	public Button(int x, int y, int screenSize[], String inAlign) {
		xOffset = x;
		yOffset = y;
		screenDims = screenSize;
		align = inAlign;
	}
	
	public boolean inBounds(int cX, int cY) {
		System.out.println(cX+" "+cY+", "+x+" "+y);
		return (x <= cX && cX <= x+width) && (y <= cY && cY <= y+height);
	}
	
	public int getState() {
		return state;
	}
	
	public Container getParent() {
		return parent;
	}
	
	public void addParent(Container c) {
		parent = c;
	}
	
	public void setState(int newState) {
		state = newState;
	}
	
	public void pressed() {
		setState(PRESSED);
	}
	
	public void clicked() {
		setState(NORMAL);
		//Action
	}
	
	public void hoveredOver() {
		setState(HOVER);
	}
	
	public void draw() {
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
	
	public void setWidth(int w) {
		width = w;
	}
	
	public void setHeight (int h) {
		height = h;
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
