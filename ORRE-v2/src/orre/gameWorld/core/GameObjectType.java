package orre.gameWorld.core;

public enum GameObjectType {
	SOUND_EFFECT(new PropertyType[]{}), 
	TRIGGER(new PropertyType[]{}),
	CAMERA_CONTROLLER(new PropertyType[]{PropertyType.KEYBOARD_CAMERA_CONTROLLER}), 
	GUI(new PropertyType[]{PropertyType.IS_GUI}), 
	DEV_TOOLS(new PropertyType[]{PropertyType.DEV_TOOLS});

	public final PropertyType[] properties;

	private GameObjectType(PropertyType[] properties) {
		this.properties = properties;
	}

}
