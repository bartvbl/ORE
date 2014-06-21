package orre.gui.controls;

import orre.geom.Point2D;
import orre.geom.Rectangle;
import orre.gui.Bounds;
import orre.gui.Corner;
import orre.gui.elementNodes.ButtonNode;
import orre.gui.gl.TextRenderer;

public class ImageButton extends Control {
	private final ButtonNode buttonNode;

	public static ImageButton create(Bounds bounds, double padding, String name) {
		ButtonNode buttonNode = new ButtonNode();
		return new ImageButton(buttonNode, bounds, name);
	}


	private ImageButton(ButtonNode node, Bounds bounds, String name) {
		super(node, bounds, name);
		this.buttonNode = node;
	}	
}
