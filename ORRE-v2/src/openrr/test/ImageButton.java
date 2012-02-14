package openrr.test;

import java.util.ArrayList;

import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

import java.io.FileNotFoundException;
import java.io.FileInputStream;

import org.lwjgl.util.Rectangle;

public class ImageButton extends Button {
	
	private Texture image;
	private Texture hoverText;
	
	private ArrayList<Texture> stateImages = new ArrayList<Texture>();
	
	public ImageButton(int x, int y, int screenSize[], String inAlign) {
		super(x, y, screenSize, inAlign);
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
				System.out.println("\t\t\t\t"+filePath+" LOADED");
			}
			else {
				stateImages.add(null);
				System.out.println("\t\t\t\t"+filePath+" NOT LOADED");
			}
		}
		
		try {
			filePath = new FileInputStream(hoverFile);
			
		} catch (FileNotFoundException e) {
			filePath = null;
		}
		
		if (filePath!=null) {
			System.out.println("\t\t\t\t"+hoverFile+" LOADED");
			stateImages.add(TextureLoader.createTextureFromImage(TextureLoader.loadImageFromFile(hoverFile)));
		}
		else {
			System.out.println("\t\t\t\t"+hoverFile+" NOT LOADED");
			stateImages.add(null);
		}
		setState(NORMAL);
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		setPosition();
		
	}
}
