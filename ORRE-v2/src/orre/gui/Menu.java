package orre.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.lwjgl.opengl.Display;

import orre.animation.Animatable;
import orre.gameWorld.core.GameWorld;
import orre.gui.controls.Control;
import orre.gui.elements.GUIElement;
import orre.resources.Resource;
import orre.resources.ResourceCache;
import orre.resources.ResourceObject;
import orre.resources.ResourceService;
import orre.resources.ResourceType;
import orre.sceneGraph.CoordinateNode;

public class Menu implements ResourceObject<Menu>, IncompleteResourceObject<?>, Animatable {
	public final String name;
	public final GUIElement root;
	
	private final HashMap<String, GUIElement> registeredElements = new HashMap<String, GUIElement>();
	private final ArrayList<Control> menuControls = new ArrayList<Control>();
	private boolean graphicsInitialised = false;
	private int activeAnimationCount = 0;
	private GameWorld world;
	private final HashMap<String, String> animationEventParams;

	public Menu(String name, GUIElement root) {
		this.name = name;
		this.root = root;//new GUIElement(new Bounds(0, 0, 0, 0, 100, 0, 100, 0), );
		findElements(root);
		animationEventParams = new HashMap<String, String>();
		animationEventParams.put("menuName", name);
	}
	
	private void findElements(GUIElement element) {
		registerGUIElement(element);
		for(GUIElement child : element.getChildren()) {
			findElements(child);
		}
	}

	public void registerGUIElement(GUIElement element) {
		this.registeredElements.put(element.name, element);
		if(element instanceof Control) {
			this.menuControls.add((Control) element);
		}
	}

	@Override
	public CoordinateNode getModelPartByName(String name) {
		return registeredElements.get(name).sceneNode;
	}

	public boolean update(double mouseX, double mouseY, boolean mouseState) {
		if(activeAnimationCount != 0) {
			return false;
		}
		updateBounds(root, 0, 0, Display.getWidth(), Display.getHeight());
		boolean hasConsumedMouse = false;
		for(Control control : menuControls) {
			boolean controlUpdateResult = control.updateMouse(mouseX, mouseY, mouseState);
			hasConsumedMouse = hasConsumedMouse || controlUpdateResult;
		}
		return hasConsumedMouse;
	}
	
	private void updateBounds(GUIElement element, float parentX, float parentY, float parentWidth, float parentHeight) {
		float x = (float) element.bounds.getX(parentWidth, parentHeight);
		float y = (float) element.bounds.getY(parentWidth, parentHeight);
		float width = (float) element.bounds.getWidth(parentWidth, parentHeight);
		float height = (float) element.bounds.getHeight(parentWidth, parentHeight);
		element.sceneNode.updateBounds(x, y, width, height);
		element.update(x + parentX, y + parentY, width, height);
		for(GUIElement child : element.getChildren()) {
			updateBounds(child, x + parentX, y + parentY, width, height);
		}
	}

	@Override
	public Resource finalizeResource() {
		for(GUIElement element : registeredElements.values()) {
			element.sceneNode.finaliseResource();
		}
		return new Resource(ResourceType.menu, this.name, Menu.class, this);
	}

	public void initGraphics(ResourceService resourceService) {
		if(!this.graphicsInitialised ) {
			initElementGraphics(root, resourceService);
		}
	}
	
	private void initElementGraphics(GUIElement element, ResourceService resourceService) {
		element.initGraphics(resourceService);
		for(GUIElement child : element.getChildren()) {
			initElementGraphics(child, resourceService);
		}
	}

	public void initEventHandlers(GameWorld world) {
		this.world = world;
		for(Control control : menuControls) {
			control.setCurrentWorld(world);
		}
	}

	public void notifyAnimationStart() {
		activeAnimationCount++;
	}

	public void notifyAnimationEnd() {
		world.services.scriptingService.dispatchScriptEvent("GUI_AnimationComplete", animationEventParams);
		world.services.scriptingService.dispatchScriptEvent("GUI_AnimationEventHandled", animationEventParams);
	}
	
	public void notifyAnimationEndHandled() {
		activeAnimationCount--;
	}
	
	public String toString() {
		return "Menu " + name;
	}

	@Override
	public String[] getModelParts() {
		Set<String> menuParts = registeredElements.keySet();
		return menuParts.toArray(new String[menuParts.size()]);
	}
}
