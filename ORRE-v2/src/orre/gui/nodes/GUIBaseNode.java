package orre.gui.nodes;

import org.lwjgl.opengl.Display;

import orre.geom.Rectangle;
import orre.gui.Corner;
import orre.sceneGraph.CoordinateNode;
import orre.sceneGraph.SceneNode;

public abstract class GUIBaseNode extends CoordinateNode implements SceneNode {
	private final Rectangle bounds;
	private final Corner orientation;
	
	public GUIBaseNode(Rectangle bounds, Corner orientation) {
		this.bounds = bounds;
		this.orientation = orientation;
	}

	@Override
	public void render() {
		double x, y;
		
		if((orientation == Corner.BOTTOM_RIGHT) || (orientation == Corner.TOP_RIGHT)) {
			x = Display.getWidth() - bounds.width - bounds.x1;
		} else {
			x = bounds.x1;
		}
		if((orientation == Corner.TOP_LEFT) || (orientation == Corner.TOP_RIGHT)) {
			y = Display.getHeight() - bounds.height - bounds.y1;
		} else {
			y = bounds.y1;
		}
		
		this.draw(x, y, x + bounds.width, y + bounds.height);
	}

	protected abstract void draw(double x1, double y1, double x2, double y2);
}
