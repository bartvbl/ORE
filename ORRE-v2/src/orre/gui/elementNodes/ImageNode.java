package orre.gui.elementNodes;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;
import orre.gl.texture.Texture;
import orre.gui.baseNodes.GUIBaseNode;

public class ImageNode extends GUIBaseNode {
	
	private Texture texture;

	public ImageNode() {
		
	}

	@Override
	protected void draw(double x1, double y1, double x2, double y2) {
		glEnable(GL_TEXTURE_2D);
		texture.bind();
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

}
