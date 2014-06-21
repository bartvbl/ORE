package orre.gui.controls;

import orre.gui.Bounds;
import orre.gui.elementNodes.ButtonNode;

public class ImageButton extends Control {
	private final ButtonNode buttonNode;

	public static ImageButton create(Bounds bounds, String name, String upImageName, String overImageName, String downImageName, String disabledImageName) {
		ButtonNode buttonNode = new ButtonNode();
		return new ImageButton(buttonNode, bounds, name);
	}

	private ImageButton(ButtonNode node, Bounds bounds, String name) {
		super(node, bounds, name);
		this.buttonNode = node;
	}

	@Override
	protected void update() {
		
	}	
}
