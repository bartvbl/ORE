package orre.gui.elements;

import orre.gui.Bounds;
import orre.sceneGraph.CoordinateNode;

public abstract class GUIElement {

	public final CoordinateNode sceneNode;
	public final String name;

	protected final Bounds bounds;
	
	public GUIElement(Bounds bounds, CoordinateNode node, String name) {
		this.bounds = bounds;
		this.sceneNode = node;
		this.name = name;
	}

}
