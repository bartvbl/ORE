package orre.gui.elementNodes;

import orre.geom.Shapes;
import orre.gl.renderer.RenderPass;
import orre.gl.renderer.RenderState;
import orre.gl.texture.Texture;
import orre.gl.vao.GeometryNode;
import orre.gui.baseNodes.GUIBaseNode;

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
	protected void draw(RenderState state, float x1, float y1, float x2, float y2) {
		if(textures[activeTextureIndex] == null) {
			return;
		}
		textures[activeTextureIndex].bind(state);
		state.transformations.pushMatrix();
		state.transformations.translate(x1, y1, 0);
		state.transformations.scale(x2 - x1, y2 - y1, 1);
		RenderPass.renderSingleNode(squareVAO, state);
		state.transformations.popMatrix();
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
