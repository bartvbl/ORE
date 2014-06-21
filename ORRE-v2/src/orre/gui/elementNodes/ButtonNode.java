package orre.gui.elementNodes;

import static org.lwjgl.opengl.GL11.*;
import orre.geom.Rectangle;
import orre.gui.Corner;
import orre.gui.baseNodes.GUIBaseNode;
import orre.gui.gl.TextRenderer;

public class ButtonNode extends GUIBaseNode {
	private boolean isHovering = false;
	
	public void setHoverState(boolean isHovering) {
		this.isHovering  = isHovering;
	}
	
	@Override
	protected void draw(double x1, double y1, double x2, double y2) {
		if(isHovering) {
			glColor4d(1, 1, 1, 0.8);
		} else {
			glColor4d(1, 1, 1, 0.5);
		}
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glVertex2d(x1, y1);
		glVertex2d(x2, y1);
		glVertex2d(x2, y2);
		glVertex2d(x1, y2);
		glEnd();
		
		glColor4d(0.3, 0.3, 0.3, 1);
		glBegin(GL_LINES);
		glVertex2d(x1, y1);
		glVertex2d(x2, y1);
		glVertex2d(x2, y1);
		glVertex2d(x2, y2);
		glVertex2d(x2, y2);
		glVertex2d(x1, y2);
		glVertex2d(x1, y2);
		glVertex2d(x1, y1);
		glEnd();
	}
	
	@Override
	public String toString() {
		return "Button node";
	}
}
