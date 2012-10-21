package openrr.test;

import org.newdawn.slick.Color;

import org.lwjgl.input.Mouse;
import static org.lwjgl.opengl.GL11.*;

import orre.gl.RenderUtils;

public class HoverText extends Text {
	
	int screenW, screenH;
	
	public HoverText(String inText, String inFont, Color inColor, int inFontSize, EventDispatcher eventDispatcher, Frame parent) {
		super(new int[] {Mouse.getX(), Mouse.getY(), 0, 0}, inText, inFont, inColor, inFontSize, eventDispatcher, parent);
		setPosData(new int[] {getPosData()[0], getPosData()[1], w, h});
		screenW = 800;
		screenH = 600;
	}

	public void draw() {
		setPosData(new int[] {Mouse.getX(), Mouse.getY()-35, getPosData()[2], getPosData()[3]});
		int[] pos = checkDisplayBounds();
		drawBackground(pos);
		RenderUtils.set2DMode();
		glDisable(GL_CULL_FACE);
		glTranslatef(0f, fontSize - 2*-pos[1], 0f);
		glScalef(1, -1, 1);
		font.drawString(pos[0], pos[1], text, color);
		glColor4f(1, 1, 1, 1);
	}
	
	public void drawBackground(int[] pos) {
		int width = 2;
		glDisable(GL_TEXTURE_2D);
		glColor4f(.051f, .51f, .059f, 1.0f);
		glBegin(GL_QUADS);
		//Left
		glVertex2f(pos[0]-5, pos[1]-6);
		glVertex2f(pos[0]-5, pos[1]+h);
		glVertex2f(pos[0]-5+width, pos[1]+h-1);
		glVertex2f(pos[0]-5+width, pos[1]-6+1);
		//Top
		glVertex2f(pos[0]-5, pos[1]+h+1);
		glVertex2f(pos[0]+1+w, pos[1]+h+1);
		glVertex2f(pos[0]+w, pos[1]+h-width+1);
		glVertex2f(pos[0]-4, pos[1]+h-width+1);
		glColor4f(.019f, .22f, .023f, 1.0f);
		//Right
		glVertex2f(pos[0]+2+w, pos[1]-6);
		glVertex2f(pos[0]+2+w, pos[1]+h+1);
		glVertex2f(pos[0]+2+w-width, pos[1]+h);
		glVertex2f(pos[0]+2+w-width, pos[1]-6+1);
		//Bottom
		glVertex2f(pos[0]-3, pos[1]-4);
		glVertex2f(pos[0]+w, pos[1]-4);
		glVertex2f(pos[0]+1+w, pos[1]-4-width);
		glVertex2f(pos[0]-4, pos[1]-4-width);
		glColor4f(.016f, .38f, .019f, 1.0f);
		//Center
		glVertex2f(pos[0]-3, pos[1]-4);
		glVertex2f(pos[0]+w, pos[1]-4);
		glVertex2f(pos[0]+w, pos[1]-1+h);
		glVertex2f(pos[0]-3, pos[1]-1+h);
		glEnd();
	}
	
	public int[] checkDisplayBounds() {
		int[] pos = new int[2];
		if (x+w+2>=screenW) {
			pos[0] = screenW-(w+2);
		} else {
			pos[0] = x;
		}
		if (y+h+1>=screenH) {
			pos[1] = screenH-(h+1);
		} else {
			pos[1] = y;
		}
		return pos;
	}
	
}
