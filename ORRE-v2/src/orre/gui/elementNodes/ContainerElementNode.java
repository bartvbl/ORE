package orre.gui.elementNodes;

import orre.gui.baseNodes.GUIBaseNode;
import orre.rendering.RenderState;

public class ContainerElementNode extends GUIBaseNode {

	public ContainerElementNode(String name) {
		super(name);
	}

	@Override
	protected void draw(RenderState state, double x1, double y1, double x2, double y2) {
	}
	
	public String toString() {
		return "GUI Container (" + name + ")";
	}

	@Override
	public void finaliseResource() {
		
	}

}
