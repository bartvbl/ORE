package orre.gui.controls;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import orre.geom.Rectangle;
import orre.gui.Corner;
import orre.gui.nodes.ButtonNode;
import orre.gui.nodes.GUIBaseNode;
import orre.sceneGraph.SceneNode;

public abstract class Control {
	public final Rectangle bounds;
	public final SceneNode sceneNode;
	public final Corner orientation;

	public Control(SceneNode node, Corner orientation, Rectangle bounds) {
		this.bounds = bounds;
		this.sceneNode = node;
		this.orientation = orientation;
	}
	
	protected boolean mouseOver() {
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();

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
		
		return (x < mouseX) && (y < mouseY) && (x + bounds.width > mouseX) && (y + bounds.height > mouseY);
	}
	
	

	public abstract void update();
}