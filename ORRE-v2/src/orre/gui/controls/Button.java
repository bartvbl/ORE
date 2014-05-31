package orre.gui.controls;

import orre.geom.Point2D;
import orre.geom.Rectangle;
import orre.gui.Corner;
import orre.gui.gl.TextRenderer;
import orre.gui.nodes.ButtonNode;

public class Button extends Control {
	private final ButtonNode buttonNode;

	public static Button create(Corner corner, Point2D location, double padding, String buttonText) {
		Rectangle bounds = new Rectangle(location.x, location.y, TextRenderer.getWidth(buttonText) + 2.0*padding, TextRenderer.getHeight(buttonText) + 2.0*padding);
		ButtonNode buttonNode = new ButtonNode(bounds, corner, padding, buttonText);
		return new Button(buttonNode, corner, bounds, buttonText);
	}


	private Button(ButtonNode node, Corner corner, Rectangle bounds, String buttonText) {
		super(node, corner, bounds);
		this.buttonNode = node;
	}

	@Override
	public void update() {
		buttonNode.setHoverState(this.mouseOver());
	}
	
}
