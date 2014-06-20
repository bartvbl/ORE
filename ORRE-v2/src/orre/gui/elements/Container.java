package orre.gui.elements;

import orre.gui.Bounds;
import orre.sceneGraph.CoordinateNode;

public class Container extends GUIElement {

	public Container(Bounds bounds, String name) {
		super(bounds, new CoordinateNode(), name);
	}

}
