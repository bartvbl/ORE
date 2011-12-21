package openrr.test;

import java.util.ArrayList;

import orre.gl.texture.Texture;

import java.io.FileNotFoundException;
import java.io.FileInputStream;

import org.lwjgl.util.Rectangle;

public class Button {
	
	public int NORMAL = 0;
	public int PRESSED = 1;
	public int INACTIVE = 2;
	public int HOVER = 3;
	
	public int x;
	public int y;
	public int width;
	public int height;
	public int xOffset;
	public int yOffset;
	
	public int[] screenDims;
	
	public Texture image;
	public Texture hoverText;
	
	public ArrayList<Texture> stateImages = new ArrayList<Texture>();
	
	public int state;
	
	public String align;
	
	public Button(int x, int y, int screenSize[], String inAlign) {
		xOffset = x;
		yOffset = y;
		screenDims = screenSize;
		align = inAlign;
	}
	
//	public int[] getBounds() {
//		return new int[] {x, x+image.width, y, y+image.height};
//	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, x+image.width, y+image.height);
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
	
	public void draw() {
		if (state==HOVER && stateImages.get(HOVER)!=null) {
			Texture hoverOverlay = stateImages.get(HOVER);
			hoverOverlay.blit(x,y,hoverOverlay.width,hoverOverlay.height);
		}
		image.blit(x, y, image.width, image.height);
	}
	
	public void loadImages(String path, String file, String hoverFile) {
		String[] types = new String[3];
		types[0] = "";
		types[1] = "p";
		types[2] = "n";
		FileInputStream filePath;
		for (int i=0; i<3;i++) {
			try {
				filePath = new FileInputStream(path+types[i]+file);
				
			} catch (FileNotFoundException e) {
				filePath = null;
			}
			
			if (filePath!=null) {
				stateImages.add(new Texture(path+types[i]+file));
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
			stateImages.add(new Texture(hoverFile));
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
				x = (screenDims[0]/2)+(image.width/2);
			}
		}
	}

}
