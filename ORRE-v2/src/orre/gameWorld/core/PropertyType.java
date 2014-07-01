package orre.gameWorld.core;

import orre.devTools.DevTools;
import orre.gameWorld.properties.TaskExecutor;
import orre.gui.GUI;
import orre.input.KeyboardCameraController;

public enum PropertyType {
	KEYBOARD_CAMERA_CONTROLLER(KeyboardCameraController.class), 
	IS_GUI(GUI.class), 
	DEV_TOOLS(DevTools.class);

	public final Class<? extends Property> propertyClass;

	private PropertyType(Class<? extends Property> propertyClass) {
		this.propertyClass = propertyClass;
	}
}
