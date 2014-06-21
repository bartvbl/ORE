package orre.gui;

import java.util.ArrayList;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gui.baseNodes.GUIRootNode;
import orre.gui.controls.Control;

public class GUI extends Property {
	private final ArrayList<Menu> activeMenus = new ArrayList<Menu>();
	private GUIRootNode guiRoot;
	
	private double mouseX;
	private double mouseY;
	private boolean mouseState;

	public GUI(GameObject object) {
		super(PropertyType.IS_GUI, object);
	}

	@Override
	public void handleMessage(Message<?> message) {
		
	}

	@Override
	public void tick() {
		for(Menu menu : activeMenus) {
			menu.update(mouseX, mouseY, mouseState);
		}
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		this.guiRoot = new GUIRootNode();
		gameObject.world.sceneRoot.addChild(guiRoot);
		gameObject.world.services.inputService.addCommandListener(this.gameObject.id, "mouseMovedX");
		gameObject.world.services.inputService.addCommandListener(this.gameObject.id, "mouseMovedY");
		gameObject.world.services.inputService.addCommandListener(this.gameObject.id, "select");
	}

}
