package openrr.test;

import static org.lwjgl.opengl.GL11.*;

import orre.gl.RenderUtils;
import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.util.ArrayList;

public class TextButton extends Button {
	
	private String text;
	private UnicodeFont font;
	private Color color;
	
	private int fontSize;
	
	private ArrayList<String> stateText = new ArrayList<String>();
	private ArrayList<Color> stateColors = new ArrayList<Color>();
	
	public TextButton(int x, int y, int screenSize[], String inAlign, String inText) {
		super(x, y, screenSize, inAlign);
	}
	
	public void setState(int newState) {
		state = newState;
		color = stateColors.get(state);
		text = stateText.get(state);
	}
	
	public void draw() {
		RenderUtils.set2DMode();
		glDisable(GL_CULL_FACE);
		glTranslatef(0f, fontSize - 2*-y, 0f);
		glScalef(1, -1, 1);
		font.drawString(x, y, text, color);
		glColor4f(1, 1, 1, 1);
	}
	
	public void loadData(int inState, String inFont, int size, String[] inText, String[] colors) {
		fontSize = size;
		font = new UnicodeFont(new Font(inFont, Font.BOLD, size));
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			System.out.println("Glyphs for "+colors[0]+" failed to load.");
		}
		for (int i=0; i<12; i+=3) {
			stateColors.add(new Color(Integer.parseInt(colors[i]), Integer.parseInt(colors[i+1]), Integer.parseInt(colors[i+2])));
		}
		for (String s : inText) {
			stateText.add(s);
		}
		color = stateColors.get(NORMAL);
		text = stateText.get(NORMAL);
		width = font.getWidth(text);
		height = font.getHeight(text);
		setPosition();
	}
}
