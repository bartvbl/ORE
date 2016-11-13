package orre.gui.elementNodes;

import static org.lwjgl.opengl.GL11.*;

import orre.geom.Shapes;
import orre.gl.texture.Texture;
import orre.gl.vao.GeometryNode;
import orre.gui.baseNodes.GUIBaseNode;
import orre.rendering.RenderState;
import orre.rendering.ShaderProperty;

public class ButtonNode extends GUIBaseNode {
	private final Texture[] textures = new Texture[4];
	private int activeTextureIndex = 0;
	private final String name;
	private GeometryNode squareVAO;
	
	public ButtonNode(String name) {
		this.name = name;
	}

	public void setUpState() {
		activeTextureIndex = 0;
	}
	
	public void setOverState() {
		activeTextureIndex = 1;
	}
	
	public void setDownState() {
		activeTextureIndex = 2;
	}
	
	public void setDisabledState() {
		activeTextureIndex = 3;
	}
	
	@Override
	protected void draw(RenderState state, double x1, double y1, double x2, double y2) {
		if(textures[activeTextureIndex] == null) {
			return;
		}
		textures[activeTextureIndex].bind(state);
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
	
	@Override
	public String toString() {
		return "Button node (" + name + ")";
	}

	public void setTextures(Texture upTexture, Texture overTexture, Texture downTexture, Texture disabledTexture) {
		this.textures[0] = upTexture;
		this.textures[1] = overTexture;
		this.textures[2] = downTexture;
		this.textures[3] = disabledTexture;
	}

	@Override
	public void finaliseResource() {
		squareVAO = Shapes.generateTexturedSquare();
	}
}
