package orre.gui.elements;

import orre.gui.Bounds;
import orre.gui.elementNodes.ContainerElementNode;
import orre.resources.ResourceService;

public class Container extends GUIElement {

	public Container(Bounds bounds, String name) {
		super(bounds, new ContainerElementNode(name), name);
	}

	@Override
	public void initGraphics(ResourceService resourceService) {}

}
