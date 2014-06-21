package orre.gui.baseNodes;

import org.lwjgl.opengl.Display;

import orre.gui.elements.Container;
import orre.gui.elements.GUIElement;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class MenuRootNode extends ContainerNode implements SceneNode {
	
	private final Container menuRoot;

	public MenuRootNode(Container menuRoot) {
		this.addChild(menuRoot.sceneNode);
		this.menuRoot = menuRoot;
	}
	
	@Override
	public void preRender() {
		
	}
}
