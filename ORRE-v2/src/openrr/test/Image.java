package openrr.test;

import java.io.File;

import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

public class Image extends GUIElement implements DrawableElement {
	
	Texture image;
	
	public Image(int posData[], String fileName, EventDispatcher eventDispatcher, Frame parent) {
		super(posData, eventDispatcher, parent);
		try {
			image = TextureLoader.createTextureFromImage(TextureLoader.loadImageFromFile(new File(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw() {
		image.blit(x, y, w, h);
	}
}
