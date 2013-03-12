package orre.gameWorld.core;

public enum GameObjectType {
	ROCK_RAIDER(new PropertyType[]{PropertyType.HEALTH}), 
	MONSTER(new PropertyType[]{}), 
	SOUND_EFFECT(new PropertyType[]{}), 
	CHRYSTAL(new PropertyType[]{}), 
	TRIGGER(new PropertyType[]{});

	public final PropertyType[] properties;

	private GameObjectType(PropertyType[] properties) {
		this.properties = properties;
	}

}
