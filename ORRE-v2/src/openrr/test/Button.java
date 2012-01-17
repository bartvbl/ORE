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
	
	private int x;
	private int y;
	private int xOffset;
	private int yOffset;
	
	private int[] screenDims;
	
	private Texture image;
	private Texture hoverText;
	
	private ArrayList<Texture> stateImages = new ArrayList<Texture>();
	
	private int state;
	
	private String align;
	
	private Container parent;
	
	public Button(int x, int y, int screenSize[], String inAlign) {
		xOffset = x;
		yOffset = y;
		screenDims = screenSize;
		align = inAlign;
	}
	
	public boolean inBounds(int cX, int cY) {
		return (x <= cX && cX <= x+image.getWidth()) && (y <= cY && cY <= y+image.getHeight());
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
		if (state==HOVER) {
			image = stateImages.get(NORMAL);
		}
		else {
			image = stateImages.get(state);
		}
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
		image.blit(x, y, image.getWidth(), image.getHeight());
		if (state==HOVER && stateImages.get(HOVER)!=null) {
			Texture hoverOverlay = stateImages.get(HOVER);
			hoverOverlay.blit(x,y,hoverOverlay.getWidth(),hoverOverlay.getHeight());
			
		}
	}
	
	public void loadImages(String pathPrefix, String file, String hoverFile) {
		String[] types = new String[3];
		types[0] = "";
		types[1] = "p";
		types[2] = "n";
		FileInputStream filePath;
		for (int i=0; i<3;i++) {
			try {
				filePath = new FileInputStream(pathPrefix+types[i]+file);
				
			} catch (FileNotFoundException e) {
				filePath = null;
			}
			
			if (filePath!=null) {
				stateImages.add(TextureLoader.createTextureFromImage(TextureLoader.loadImageFromFile(pathPrefix+types[i]+file)));
			}
			else {
				stateImages.add(null);
			}
		}
		
		try {
			filePath = new FileInputStream(hoverFile);
			
		} catch (FileNotFoundException e) {
			filePath = null;
		}
		
		if (filePath!=null) {
			stateImages.add(TextureLoader.createTextureFromImage(TextureLoader.loadImageFromFile(hoverFile)));
		}
		else {
			stateImages.add(null);
		}
		setState(NORMAL);
		setPosition();
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
				x = (screenDims[0]/2)+(image.getWidth()/2);
			}
		}
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
