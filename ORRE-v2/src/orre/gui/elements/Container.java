package orre.gui.elements;

import orre.geom.Rectangle;
import orre.gui.Corner;
import orre.sceneGraph.CoordinateNode;

public class Container extends GUIElement {

	public Container(Rectangle bounds, Corner orientation, String name) {
		super(bounds, orientation, new CoordinateNode(), name);
	}

}
