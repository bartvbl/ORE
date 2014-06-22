package orre.gui.baseNodes;

import orre.sceneGraph.CoordinateNode;
import orre.sceneGraph.SceneNode;

public abstract class GUIBaseNode extends CoordinateNode implements SceneNode {
	private double width;
	private double height;

	public GUIBaseNode() {
		
	}
	
	public void updateBounds(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render() {
		this.draw(x, y, x + width, y + height);
	}

	protected abstract void draw(double x1, double y1, double x2, double y2);

}
