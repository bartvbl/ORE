package orre.gui.elementNodes;

import orre.gui.baseNodes.GUIBaseNode;

public class ContainerElementNode extends GUIBaseNode {

	public ContainerElementNode(String name) {
		super(name);
	}

	@Override
	protected void draw(double x1, double y1, double x2, double y2) {
	}
	
	public String toString() {
		return "GUI Container (" + name + ")";
	}

}
