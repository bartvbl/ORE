package orre.gui.elements;

import orre.gui.Bounds;
import orre.sceneGraph.CoordinateNode;

public class ImageElement extends GUIElement {

	private final String imageName;

	public ImageElement(Bounds bounds, String name, String imageName) {
		super(bounds, new CoordinateNode(), name);
		this.imageName = imageName;
	}

}
