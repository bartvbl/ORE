package openrr.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

public class Graphic {
	
	int x;
	int y;
	int xOffset;
	int yOffset;
	int width;
	int height;
	
	int[] screenDims;
	
	Texture image;
	
	String align;
	
	public Graphic(int x, int y, int[] screenSize, String filePath, String inAlign) {
		xOffset = x;
		yOffset = y;
		try {
			image = TextureLoader.createTextureFromImage(TextureLoader.loadImageFromFile(new File(filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		width = image.getWidth();
		height = image.getHeight();
		screenDims = screenSize;
		align = inAlign;
		setPosition();
	}
	
	public void changeWidth(int w) {
		width = w;
	}
	
	public void changeHeight(int h) {
		height = h;
	}
	
	public void changeX(int shiftX) {
		xOffset = xOffset+shiftX;
		setPosition();
	}
	
	public void draw() {
		image.blit(x, y, width, height);
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
}
