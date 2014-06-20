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
	private GUIRootNode guiRoot;

	public GUI(GameObject object) {
		super(PropertyType.IS_GUI, object);
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
		this.guiRoot = new GUIRootNode();
		gameObject.world.sceneRoot.addChild(guiRoot);
	}

}
