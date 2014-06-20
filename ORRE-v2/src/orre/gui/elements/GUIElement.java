package orre.gui.elements;

import orre.geom.Rectangle;
import orre.gui.Corner;
import orre.sceneGraph.SceneNode;

public abstract class GUIElement {

	public final SceneNode sceneNode;
	public final String name;

	protected final Rectangle bounds;
	protected final Corner orientation;

	
	public GUIElement(Rectangle bounds, Corner orientation, SceneNode node, String name) {
		this.bounds = bounds;
		this.orientation = orientation;
		this.sceneNode = node;
		this.name = name;
	}

}
