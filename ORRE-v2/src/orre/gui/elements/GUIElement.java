package orre.gui.elements;

import java.util.ArrayList;

import orre.gui.Bounds;
import orre.gui.baseNodes.GUIBaseNode;
import orre.resources.ResourceCache;
import orre.resources.ResourceService;
import orre.sceneGraph.CoordinateNode;

public abstract class GUIElement {

	public final GUIBaseNode sceneNode;
	public final String name;
	public final Bounds bounds;
	
	private final ArrayList<GUIElement> childElements = new ArrayList<GUIElement>();
	
	public GUIElement(Bounds bounds, GUIBaseNode node, String name) {
		this.bounds = bounds;
		this.sceneNode = node;
		this.name = name;
	}
	
	public void addChild(GUIElement child) {
		this.childElements.add(child);
	}

	public ArrayList<GUIElement> getChildren() {
		return childElements;
	}

	public void update(double x, double y, double width, double height) {}

	public abstract void initGraphics(ResourceService resourceService);
}
