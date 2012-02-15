package openrr.test;

import static org.lwjgl.opengl.GL11.*;

import orre.gl.RenderUtils;
import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class TextButton extends Button {
	
	private String text;
	private UnicodeFont font;
	private Color buttonTextColour = Color.black;
	
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
			this.buttonTextColour = Color.white;
		}
		else {
			text = "TEST";
			this.buttonTextColour = Color.blue;
		}
	}
	
	public void draw() {
		RenderUtils.set2DMode();
		glDisable(GL_CULL_FACE);
		glTranslatef(0f, 24 - 2*-y, 0f);//24 = font size (I think)
		glScalef(1, -1, 1);
		font.drawString(x, y, text, this.buttonTextColour);
		glColor4f(1, 1, 1, 1);
	}
}
