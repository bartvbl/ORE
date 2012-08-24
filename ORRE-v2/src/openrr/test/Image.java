package openrr.test;

import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

public class Image extends GUIElement implements DrawableElement {
	
	Texture image;
	
	public Image(int posData[], String fileName, Frame parent) {
		super(posData, parent);
		image = TextureLoader.createTextureFromImage(TextureLoader.loadImageFromFile(fileName));
	}
	
	public void draw() {
		image.blit(x, y, w, h);
	}
}
