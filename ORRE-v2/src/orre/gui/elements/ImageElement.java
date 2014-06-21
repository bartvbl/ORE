package orre.gui.elements;

import orre.gui.Bounds;
import orre.gui.elementNodes.ImageNode;

public class ImageElement extends GUIElement {

	private final String imageName;

	public ImageElement(Bounds bounds, String name, String imageName) {
		super(bounds, new ImageNode(), name);
		this.imageName = imageName;
	}

}
