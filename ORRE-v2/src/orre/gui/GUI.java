package orre.gui;

import java.util.ArrayList;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.geom.Point2D;
import orre.gui.controls.Button;
import orre.gui.controls.Control;
import orre.gui.nodes.GUIRootNode;

public class GUI extends Property {
	private final ArrayList<Control> topLevelControls = new ArrayList<Control>();
	private final GUIRootNode guiRoot;

	public GUI(GameObject object) {
		super(PropertyType.IS_GUI.toString(), object);
		this.guiRoot = new GUIRootNode();
		object.world.rootNode.addChild(guiRoot);
	}

	@Override
	public void handleMessage(Message<?> message) {
		
	}

	@Override
	public void tick() {
		for(Control control : topLevelControls) {
			control.update();
		}
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		Button button = Button.create(Corner.TOP_RIGHT, new Point2D(8, 8), 5, "This is a test button");
		guiRoot.addChild(button.sceneNode);
		topLevelControls.add(button);
	}

}
