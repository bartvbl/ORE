package orre.gui.baseNodes;

import orre.gl.renderer.RenderState;
import orre.sceneGraph.CoordinateNode;
import orre.sceneGraph.SceneNode;

public abstract class GUIBaseNode extends CoordinateNode implements SceneNode {
	private float width;
	private float height;
	
	public GUIBaseNode() {
		
	}

	public GUIBaseNode(String name) {
		super(name);
	}
	
	public void updateBounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(RenderState state) {
		this.draw(state, 0, 0, width, height);
	}

	protected abstract void draw(RenderState state, float x1, float y1, float x2, float y2);

	public abstract void finaliseResource();

}
