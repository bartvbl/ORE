package openrr.test;

import java.util.ArrayList;
import java.util.HashMap;

import openrr.test.guiHandlers.ButtonActionHandler;
import openrr.test.guiHandlers.HoverTextDecoration;
import openrr.test.ButtonID;
import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;
import orre.util.StringUtils;


import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOError;

import org.lwjgl.util.Rectangle;

public class ImageButton extends Button implements DrawableElement {
	
	//private ArrayList<Texture> stateImages = new ArrayList<Texture>();
	private HashMap<String, Texture> stateImages= new HashMap<String, Texture>();
	ArrayList<EventType> releaseEventTypes = new ArrayList<EventType>();
	HashMap<EventType, Object> releaseEventParameterObjects = new HashMap<EventType, Object>();
	
	public ImageButton(int posData[], String startingState, EventDispatcher eventDispatcher, String fileName, String hoverFile, String hoverText, String inButtonID, Frame parent) {
		super(posData, startingState, inButtonID, eventDispatcher, parent);
		addImages(fileName, hoverFile);
		addEventListener(EventType.MOUSE_MOVE, new ButtonActionHandler(this));
		addEventListener(EventType.MOUSE_MOVE, new HoverTextDecoration(this, hoverText, getEventDispatcher(), parent));
	}
	
	public void draw() {
		if (stateImages.get(getState())!=null) {
			stateImages.get(getState()).blit(x, y, w, h);
		}
		else {
			stateImages.get("n").blit(x, y, w, h);
		}
	}
	
	public boolean addImage(String state, String imageFile) {
		if (fileExists(imageFile)) {
			stateImages.put(state, loadImage(imageFile));
			return true;
		}
		else {
			stateImages.put(state, null);
			return false;
		}
	}
	
	public void addImages(String imageFile, String hoverFile) {
		stateImages.put("n", loadImage(imageFile));
		String[] dirs = imageFile.split("/");
		String[] dirsWanted = new String[dirs.length-1];
		for (int i=0; i<dirs.length-1; i++) {
			dirsWanted[i] = dirs[i];
		}
		String basePath = StringUtils.join(dirsWanted, "/");
		String fileName = dirs[dirs.length-1];
		addImage("p", basePath+"/p"+fileName);
		if (!addImage("h", basePath+"/h"+fileName) && hoverFile!="") {
			addHoverImage(basePath, fileName, hoverFile);
		}
		addImage("i", basePath+"/i"+fileName);
	}
	
	public boolean fileExists(String fileName) {
		try {
			FileInputStream filePath = new FileInputStream(fileName);
			return true;
			
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	public Texture loadImage(String fileName) {
		return TextureLoader.createTextureFromImage(TextureLoader.loadImageFromFile(fileName));
	}
	
	public void addHoverImage(String basePath, String fileName, String hoverFilePath) {
		try {
			BufferedImage base = ImageIO.read(new File(basePath+"/"+fileName));
			BufferedImage hoverOverlay = ImageIO.read(new File(hoverFilePath));
			BufferedImage hoverImage = new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB);
			hoverImage.createGraphics().drawImage(base, 0, 0, null); 
			hoverImage.createGraphics().drawImage(hoverOverlay, 0, 0, null);
			ImageIO.write(hoverImage, "bmp", new File(basePath+"/h"+fileName));
			addImage("h", basePath+"/h"+fileName);
		} catch (IOException ioe) {
			System.out.println("CREATION OF HOVER IMAGE @ '"+basePath+"/"+fileName+"' FAILED");
		}
	}
}
