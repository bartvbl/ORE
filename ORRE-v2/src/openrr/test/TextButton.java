package openrr.test;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import orre.gl.texture.Texture;
import orre.resources.loaders.TextureLoader;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class TextButton extends Button {
	
	String text;
	UnicodeFont font;
	
	public TextButton(int x, int y, int screenSize[], String inAlign, String inText) {
		super(x, y, screenSize, inAlign);
		font = new UnicodeFont(new Font("Arial", Font.BOLD, 24));
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		text = inText;
	}
	
	public boolean inBounds(int cX, int cY) {
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
		glTranslatef(0f, 2*y, 0f);
		glScalef(1, -1, 1);
		glDisable(GL_CULL_FACE);
		font.drawString(x, y, text);
	}
}
