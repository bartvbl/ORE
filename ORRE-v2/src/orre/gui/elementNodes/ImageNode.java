package orre.gui.elementNodes;

import static org.lwjgl.opengl.GL11.*;
import orre.gl.texture.Texture;
import orre.gui.baseNodes.GUIBaseNode;
import orre.rendering.RenderState;

public class ImageNode extends GUIBaseNode {
	
	private Texture texture;

	public ImageNode(String name) {
		super(name);
	}

	@Override
	protected void draw(RenderState state, double x1, double y1, double x2, double y2) {
		glColor4d(1, 1, 1, 1);
		glEnable(GL_TEXTURE_2D);
		texture.bind(state);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex2d(x1, y1);
		glTexCoord2d(1, 0);
		glVertex2d(x2, y1);
		glTexCoord2d(1, 1);
		glVertex2d(x2, y2);
		glTexCoord2d(0, 1);
		glVertex2d(x1, y2);
		glEnd();
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	@Override
	public void finaliseResource() {
		
	}
	
	public String toString() {
		return "Image node (" + name + ")";
	}

}
