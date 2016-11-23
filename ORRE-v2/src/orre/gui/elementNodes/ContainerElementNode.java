package orre.gui.elementNodes;

import orre.gl.renderer.RenderState;
import orre.gui.baseNodes.GUIBaseNode;

public class ContainerElementNode extends GUIBaseNode {

	public ContainerElementNode(String name) {
		super(name);
	}

	@Override
	protected void draw(RenderState state, float x1, float y1, float x2, float y2) {
	}
	
	public String toString() {
		return "GUI Container (" + name + ")";
	}

	@Override
	public void finaliseResource() {
		
	}

}
