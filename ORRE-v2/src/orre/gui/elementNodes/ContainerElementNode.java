package orre.gui.elementNodes;

import orre.gui.baseNodes.GUIBaseNode;

public class ContainerElementNode extends GUIBaseNode {

	@Override
	protected void draw(double x1, double y1, double x2, double y2) {
		this.setLocation(x1, y1, 0);
	}

}
