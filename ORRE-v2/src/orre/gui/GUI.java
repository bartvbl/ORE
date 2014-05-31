package orre.gui;

import java.util.ArrayList;

import orre.geom.Point2D;
import orre.gui.controls.Button;
import orre.gui.controls.Control;
import orre.gui.nodes.GUIRootNode;
import orre.sceneGraph.ContainerNode;

public class GUI {
	private final ArrayList<Control> topLevelControls = new ArrayList<Control>();
	private final GUIRootNode guiRoot;

	public GUI(ContainerNode sceneRoot) {
		this.guiRoot = new GUIRootNode();
		sceneRoot.addChild(guiRoot);
		Button button = Button.create(Corner.TOP_RIGHT, new Point2D(8, 8), 5, "This is a test button");
		guiRoot.addChild(button.sceneNode);
		topLevelControls.add(button);
	}

	public void update() {
		for(Control control : topLevelControls) {
			control.update();
		}
	}

}
