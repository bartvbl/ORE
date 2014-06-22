package orre.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;

import orre.animation.Animatable;
import orre.gui.controls.Control;
import orre.gui.elements.Container;
import orre.gui.elements.GUIElement;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.sceneGraph.CoordinateNode;

public class Menu implements Animatable, Finalizable {
	public final String name;
	public final GUIElement root;
	
	private final HashMap<String, GUIElement> registeredElements = new HashMap<String, GUIElement>();
	private final ArrayList<Control> menuControls = new ArrayList<Control>();
	private boolean graphicsInitialised = false;

	public Menu(String name, GUIElement root) {
		this.name = name;
		this.root = root;
		findElements(root);
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
	
	public void setAnimationActive(boolean isActive) {
		
	}

	@Override
	public CoordinateNode getModelPartByName(String name) {
		return registeredElements.get(name).sceneNode;
	}

	public void update(double mouseX, double mouseY, boolean mouseState) {
		updateBounds(root, 0, 0, Display.getWidth(), Display.getHeight());
		for(Control control : menuControls) {
			control.updateMouse(mouseX, mouseY, mouseState);
		}
	}
	
	private void updateBounds(GUIElement element, double parentX, double parentY, double parentWidth, double parentHeight) {
		double x = element.bounds.getX(parentWidth, parentHeight);
		double y = element.bounds.getY(parentWidth, parentHeight);
		double width = element.bounds.getWidth(parentWidth, parentHeight);
		double height = element.bounds.getHeight(parentWidth, parentHeight);
		element.sceneNode.updateBounds(x, y, width, height);
		element.update(x + parentX, y + parentY, width, height);
		for(GUIElement child : element.getChildren()) {
			updateBounds(child, x + parentX, y + parentY, width, height);
		}
	}

	@Override
	public Resource finalizeResource() {
		return new Resource(ResourceType.menu, this.name, Menu.class, this);
	}

	public void initGraphics(ResourceCache resourceCache) {
		if(!this.graphicsInitialised ) {
			initElementGraphics(root, resourceCache);
		}
	}
	
	private void initElementGraphics(GUIElement element, ResourceCache resourceCache) {
		element.initGraphics(resourceCache);
		for(GUIElement child : element.getChildren()) {
			initElementGraphics(child, resourceCache);
		}
	}
}
