package orre.gui.elementNodes;

import static org.lwjgl.opengl.GL11.*;

import orre.geom.Shapes;
import orre.gl.renderer.RenderPass;
import orre.gl.texture.Texture;
import orre.gl.vao.GeometryNode;
import orre.gui.baseNodes.GUIBaseNode;
import orre.rendering.RenderState;

public class ImageNode extends GUIBaseNode {
	
	private Texture texture;
	private GeometryNode squareVAO;

	public ImageNode(String name) {
		super(name);
	}

	@Override
	protected void draw(RenderState state, float x1, float y1, float x2, float y2) {
		texture.bind(state);
		state.transformations.pushMatrix();
		state.transformations.scale(x2 - x1, y2 - y1, 1);
		state.transformations.translate(x1, y1, 0);
		RenderPass.renderSingleNode(squareVAO, state);
		state.transformations.popMatrix();
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	@Override
	public void finaliseResource() {
		squareVAO = Shapes.generateTexturedSquare();
	}
	
	public String toString() {
		return "Image node (" + name + ")";
	}

}
