package openrr.test;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import orre.gl.RenderUtils;
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
		font = new UnicodeFont(new Font("Arial", Font.PLAIN, 24));
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		text = inText;
		width = font.getWidth(text);
		height = font.getHeight(text);
		setPosition();
	}
	
	public void setState(int newState) {
		state = newState;
		if (state==HOVER) {
			text = "BLARGH";
			font.getEffects().add(new ColorEffect(java.awt.Color.BLUE)); 
		}
		else {
			font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
			text = "TEST";
		}
	}
	
	public void draw() {
		RenderUtils.set2DMode();
		glDisable(GL_CULL_FACE);
		glTranslatef(0f, 24 - 2*-y, 0f);//24 = font size (I think)
		glScalef(1, -1, 1);
		font.drawString(x, y, text);
	}
}
