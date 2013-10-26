package orre.gameWorld.core;

public enum GameObjectType {
	ROCK_RAIDER(new PropertyType[]{PropertyType.HEALTH}), 
	MONSTER(new PropertyType[]{}), 
	SOUND_EFFECT(new PropertyType[]{}), 
	CHRYSTAL(new PropertyType[]{}), 
	TRIGGER(new PropertyType[]{}),
	CAMERA_CONTROLLER(new PropertyType[]{PropertyType.KEYBOARD_CAMERA_CONTROLLER, PropertyType.MOUSE_CAMERA_CONTROLLER});

	public final PropertyType[] properties;

	private GameObjectType(PropertyType[] properties) {
		this.properties = properties;
	}

}
