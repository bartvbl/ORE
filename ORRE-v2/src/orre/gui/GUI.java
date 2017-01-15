package orre.gui;

import java.util.ArrayList;
import java.util.HashMap;

import orre.animation.Animation;
import orre.animation.AnimationBehaviour;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;
import orre.gl.camera.OrthographicCameraNode;
import orre.gui.baseNodes.GUIRootNode;
import orre.gui.controls.Control;
import orre.input.InputEvent;
import orre.resources.ResourceType;
import orre.resources.partiallyLoadables.Shader;
import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SceneRootNode;
import orre.util.Logger;
import orre.util.Logger.LogType;

public class GUI extends Property {
	private final ArrayList<Menu> activeMenus = new ArrayList<Menu>();
	private final ArrayList<Menu> inactiveMenus = new ArrayList<Menu>();
	private final HashMap<Integer, Menu> animatedMenus = new HashMap<Integer, Menu>();
	private GUIRootNode guiRoot;
	
	private double mouseX;
	private double mouseY;
	private boolean mouseState;
	private Shader defaultShader;

	public GUI(GameObject object) {
		super(PropertyType.IS_GUI, object);
	}

	@Override
	public void handleMessage(Message<?> message) {
		if(message.type == MessageType.SHOW_MENU) {
			showMenu((String)message.getPayload());
		} else if(message.type == MessageType.HIDE_MENU) {
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
			updateMenus(event);
		} else if(message.type == MessageType.ANIMATE_MENU) {
			AnimateMenuCommand command = (AnimateMenuCommand) message.getPayload();
			Animation animation = (Animation) gameObject.world.resourceService.getResource(ResourceType.animation, command.animationName).content;
			Menu menu = getMenuByName(command.menuName, activeMenus);
			if(menu != null) {
				int animationID = this.gameObject.world.services.animationService.applyAnimation(animation, menu, AnimationBehaviour.END_ON_COMPLETE);
				this.animatedMenus.put(animationID, menu);
				menu.notifyAnimationStart();
			} else {
				Logger.log("Menu " + command.menuName + " can not be animated, as the menu is not currently visible.", LogType.ERROR);
			}
		} else if(message.type == MessageType.ANIMATION_END_HANDLED) {
			String menuName = (String) message.getPayload();
			Menu menu = getMenuByName(menuName, activeMenus);
			if(menu != null) {
				menu.notifyAnimationEndHandled();
			} else {
				Menu inactiveMenu = getMenuByName(menuName, inactiveMenus);
				if(inactiveMenu != null) {
					inactiveMenu.notifyAnimationEndHandled();
				}
			}
		} else if(message.type == MessageType.ANIMATION_ENDED) {
			int animationID = (int) message.getPayload();
			Menu menu = this.animatedMenus.get(animationID);
			if(menu != null) {
				menu.notifyAnimationEnd();
			}
		}
	}

	@Override
	public void tick() {
		
	}

	private void updateMenus(InputEvent event) {
		for(Menu menu : activeMenus) {
			if(menu.update(mouseX, mouseY, mouseState)) {
				event.consume();
			}
		}
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		this.defaultShader = ((Shader) gameObject.world.resourceService.getResource(ResourceType.shader, "default").content);
		
		this.guiRoot = new GUIRootNode();
		OrthographicCameraNode camera = new OrthographicCameraNode();
		
		SceneNode defaultShaderNode = defaultShader.createSceneNode();
		defaultShaderNode.addChild(guiRoot);
		camera.addChild(defaultShaderNode);
		
		((SceneRootNode) gameObject.world.sceneRoot).addGUINode(camera);
		gameObject.world.addMessageListener(MessageType.ANIMATION_ENDED, this.gameObject);
		
		// GUI has highest priority, and therefore inhibits priority 0
		gameObject.world.services.inputService.addCommandListener(this.gameObject.id, "mouseMovedX", 0);
		gameObject.world.services.inputService.addCommandListener(this.gameObject.id, "mouseMovedY", 0);
		gameObject.world.services.inputService.addCommandListener(this.gameObject.id, "select", 0);
	}
	
	private Menu getMenuByName(String menuName, ArrayList<Menu> menuList) {
		for(Menu menu : menuList) {
			if(menu.name.equals(menuName)) {
				return menu;
			}
		}
		return null;
	}
	
	private void showMenu(String menuName) {
		if(getMenuByName(menuName, activeMenus) == null) {
			Menu menu = (Menu) gameObject.world.resourceService.getResource(ResourceType.menu, menuName).content;
			menu.initGraphics(gameObject.world.resourceService);
			menu.initEventHandlers(gameObject.world);
			activeMenus.add(menu);
			if(inactiveMenus.contains(menu)) {
				inactiveMenus.remove(menu);
			}
			guiRoot.addChild(menu.root.sceneNode);
			menu.update(mouseX, mouseY, mouseState);
		} else {
			Logger.log("Menu '"+menuName+"' can not be shown. It might already be visible, or it doesn't exist.", LogType.ERROR);
		}
	}
	
	private void hideMenu(String menuName) {
		Menu menu = getMenuByName(menuName, activeMenus);
		if(menu != null) {
			activeMenus.remove(menu);
			inactiveMenus.add(menu);
			guiRoot.removeChild(menu.root.sceneNode);
		}
	}

}
