package openrr.test;

import java.util.ArrayList;

import openrr.test.GUIElement;

public class Frame extends GUIElement {
	
	protected ArrayList<GUIElement> children = new ArrayList<GUIElement>();
	
	public Frame(int posData[], EventDispatcher eventDispatcher, Frame parent) {
		super(posData, eventDispatcher, parent);
	}
	
	public void addChild(GUIElement child) {
		children.add(child);
	}
	
	public void removeChild(GUIElement child) {
		children.remove(child);
	}
	
	public ArrayList<GUIElement> getChildren() {
		return children;
	}
	
	public GUIElement findChild(int coords[]) {
		for (GUIElement child : children) {
			if (child.inCoords(coords)) {
				return child;
			}
		}
		return null;
	}
}
