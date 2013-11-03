package orre.gameWorld.core;

public enum GameObjectType {
	ROCK_RAIDER(new PropertyType[]{PropertyType.HEALTH, PropertyType.GRAVITY, PropertyType.ROCK_RAIDER_APPEARANCE, PropertyType.TASK_EXECUTOR}), 
	MONSTER(new PropertyType[]{}), 
	SOUND_EFFECT(new PropertyType[]{}), 
	CHRYSTAL(new PropertyType[]{PropertyType.TRANSPORTABLE, PropertyType.GRAVITY, PropertyType.CHRYSTAL_APPEARANCE}),
	ORE(new PropertyType[]{PropertyType.TRANSPORTABLE, PropertyType.GRAVITY, PropertyType.ORE_APPEARANCE}),
	TRIGGER(new PropertyType[]{}),
	CAMERA_CONTROLLER(new PropertyType[]{PropertyType.KEYBOARD_CAMERA_CONTROLLER}), 
	LIGHT(new PropertyType[]{PropertyType.LIGHT});

	public final PropertyType[] properties;

	private GameObjectType(PropertyType[] properties) {
		this.properties = properties;
	}

}
