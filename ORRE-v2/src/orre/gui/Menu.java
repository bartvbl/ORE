package orre.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;

import orre.animation.Animatable;
import orre.gui.controls.Control;
import orre.gui.elements.Container;
import orre.gui.elements.GUIElement;
import orre.sceneGraph.CoordinateNode;

public class Menu implements Animatable {
	public final String name;
	public final Container root;
	
	private final HashMap<String, GUIElement> registeredElements = new HashMap<String, GUIElement>();
	private final ArrayList<Control> menuControls = new ArrayList<Control>();

	public Menu(String name, Container root) {
		this.name = name;
		this.root = root;
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
		updateBounds(root, Display.getWidth(), Display.getHeight());
		for(Control control : menuControls) {
			control.updateMouse(mouseX, mouseY, mouseState);
		}
	}
	
	private void updateBounds(GUIElement element, double parentWidth, double parentHeight) {
		double x = element.bounds.getX(parentWidth, parentHeight);
		double y = element.bounds.getY(parentWidth, parentHeight);
		double width = element.bounds.getWidth(parentWidth, parentHeight);
		double height = element.bounds.getHeight(parentWidth, parentHeight);
		element.sceneNode.updateBounds(x, y, width, height);
		element.update(x, y, width, height);
		for(GUIElement child : element.getChildren()) {
			updateBounds(child, width, height);
		}
	}
}
