package orre.gameWorld.core;

import orre.ai.tasks.Task;
import orre.sceneGraph.Camera;

public enum MessageType {
	OBJECT_CONTROL_RELEASED(GraphicsObject.class), 
	OBJECT_CONTROL_GAINED(GraphicsObject.class), ASSUME_CAMERA_CONTROL(Camera.class), 
	NEW_TASK(Task.class);
	
	public final Class<?> requiredPayloadDataType;
	
	private MessageType(Class<?> dataType) {
		this.requiredPayloadDataType = dataType;
	}
}
