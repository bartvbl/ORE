package orre.gui.gl;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;

import java.awt.Font;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.opengl.TextureImpl;
//needs a reimplementation
@Deprecated
public class TextRenderer {
	private static final UnicodeFont font = new UnicodeFont(new Font("Arial", Font.BOLD, 12));
	
	static {
		font.addAsciiGlyphs();
		List<Effect> effects = font.getEffects();
		effects.add(new ColorEffect(java.awt.Color.BLACK));
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static void drawString(double x, double y, String string) {
		glPushMatrix();
		glScaled(1, -1, 1);
		glTranslated(0, -14 + -2 * y, 0);
		TextureImpl.bindNone();
		Color fontColour = Color.black;
		font.drawString((float)x, (float)y, string, fontColour);
		TextureImpl.unbind();
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
	
	public static int getWidth(String string) {
		return font.getWidth(string);
	}
	
	public static int getHeight(String string) {
		return font.getHeight(string);
	}
}
