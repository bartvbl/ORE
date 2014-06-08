package orre.gameWorld.core;

import orre.gameWorld.properties.TaskExecutor;
import orre.gui.GUI;
import orre.input.KeyboardCameraController;

public enum PropertyType {
	KEYBOARD_CAMERA_CONTROLLER(KeyboardCameraController.class), 
	TASK_EXECUTOR(TaskExecutor.class), 
	IS_GUI(GUI.class);

	public final Class<?> propertyClass;

	private PropertyType(Class<? extends Property> propertyClass) {
		this.propertyClass = propertyClass;
	}
}
