package orre.gui;

import java.util.ArrayList;

import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gui.baseNodes.GUIRootNode;
import orre.gui.controls.Control;
import orre.input.InputEvent;
import orre.resources.ResourceType;

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
		if(message.type == MessageType.SHOW_MENU) {
			showMenu((String)message.getPayload());
		} else if(message.type == MessageType.SHOW_MENU) {
			hideMenu((String)message.getPayload());
		} else if(message.type == MessageType.INPUT_EVENT) {
			InputEvent event = (InputEvent) message.getPayload();
			if(event.command.equals("mouseMovedX")) {
				mouseX = event.value;
			} else if(event.command.equals("mouseMovedY")) {
				mouseY = event.value;
			} else if(event.command.equals("select")) {
				mouseState = event.value == 1.0;
			}
			updateMenus();
		}
	}

	@Override
	public void tick() {
		for(Menu menu : activeMenus) {
			menu.update(mouseX, mouseY, mouseState);
		}
		
	}
	
	private void updateMenus() {
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
		updateMenus();
	}
	
	private void showMenu(String menuName) {
		Menu menu = (Menu) gameObject.world.resourceCache.getResource(ResourceType.menu, menuName).content;
		if(!activeMenus.contains(menu)) {
			menu.initGraphics(gameObject.world.resourceCache);
			activeMenus.add(menu);
			guiRoot.addChild(menu.root.sceneNode);
		}
	}
	
	private void hideMenu(String menuName) {
		Menu menu = (Menu) gameObject.world.resourceCache.getResource(ResourceType.menu, menuName).content;
		if(activeMenus.contains(menu)) {
			activeMenus.remove(menu);
			guiRoot.removeChild(menu.root.sceneNode);
		}
	}

}
