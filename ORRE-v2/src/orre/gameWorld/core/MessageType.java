package orre.gameWorld.core;

import orre.sceneGraph.Camera;

public enum MessageType {
	OBJECT_CONTROL_RELEASED(GraphicsObject.class), 
	OBJECT_CONTROL_GAINED(GraphicsObject.class), ASSUME_CAMERA_CONTROL(Camera.class);
	
	public final Class<?> requiredPayloadDataType;
	
	private MessageType(Class<?> dataType) {
		this.requiredPayloadDataType = dataType;
	}
}
