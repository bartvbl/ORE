package openrr.test;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import orre.gl.RenderUtils;

public class Text extends GUIElement implements DrawableElement {
	
	protected String text;
	protected UnicodeFont font;
	protected Color color;
	
	protected int fontSize;
	
	public Text(int[] pos, String inText, String inFont, Color inColor, int inFontSize, EventDispatcher eventDispatcher, Frame parent) {
		super(new int[] {pos[0], pos[1], 0, 0}, eventDispatcher, parent);
		text = inText;
		fontSize = inFontSize;
		color = inColor;
		font = new UnicodeFont(new Font(inFont, Font.BOLD, fontSize));
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect(java.awt.Color.WHITE)); 
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			System.out.println("Glyphs for "+inColor+" failed to load.");
		}
		setPosData(new int[] {getPosData()[0], getPosData()[1], font.getWidth(text), font.getHeight(text)});
	}
	
	public void draw() {
		RenderUtils.set2DMode();
		glDisable(GL_CULL_FACE);
		glTranslatef(0f, fontSize - 2*-y, 0f);
		glScalef(1, -1, 1);
		font.drawString(x, y, text, color);
		glColor4f(1, 1, 1, 1);
	}
}
