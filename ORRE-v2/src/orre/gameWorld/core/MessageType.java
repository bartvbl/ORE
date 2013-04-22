package orre.gameWorld.core;

public enum MessageType {
	OBJECT_CONTROL_RELEASED(GraphicsObject.class), 
	OBJECT_CONTROL_GAINED(GraphicsObject.class);
	
	public final Class<?> requiredPayloadDataType;
	
	private MessageType(Class<?> dataType) {
		this.requiredPayloadDataType = dataType;
	}
}
