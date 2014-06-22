package orre.gameWorld.core;

import orre.ai.tasks.Task;
import orre.input.InputEvent;
import orre.sceneGraph.Camera;

public enum MessageType {
	OBJECT_CONTROL_RELEASED(GraphicsObject.class), 
	OBJECT_CONTROL_GAINED(GraphicsObject.class), ASSUME_CAMERA_CONTROL(Camera.class), 
	NEW_TASK(Task.class), 
	INPUT_EVENT(InputEvent.class), 
	SHOW_MENU(String.class),
	HIDE_MENU(String.class),
	ANIMATE_MENU(String.class);

	public final Class<?> requiredPayloadDataType;

	private MessageType(final Class<?> dataType) {
		this.requiredPayloadDataType = dataType;
	}
}
