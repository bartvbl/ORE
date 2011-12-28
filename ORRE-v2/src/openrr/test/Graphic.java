package openrr.test;

import orre.gl.texture.Texture;
import orre.gl.texture.TextureLoader;

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
		image = TextureLoader.createTextureFromImage(TextureLoader.loadImageFromFile(filePath));
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
