package orre.gui;

import java.util.HashMap;

import orre.animation.Animatable;
import orre.gui.elements.Container;
import orre.gui.elements.GUIElement;
import orre.sceneGraph.CoordinateNode;

public class Menu implements Animatable {
	public final String name;
	public final Container root;
	
	private final HashMap<String, GUIElement> registeredElements = new HashMap<String, GUIElement>();

	public Menu(String name, Container root) {
		this.name = name;
		this.root = root;
	}
	
	public void registerGUIElement(GUIElement element) {
		this.registeredElements.put(element.name, element);
	}

	@Override
	public CoordinateNode getModelPartByName(String name) {
		return registeredElements.get(name).sceneNode;
	}
}
