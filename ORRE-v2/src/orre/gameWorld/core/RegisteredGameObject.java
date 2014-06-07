package orre.gameWorld.core;

public class RegisteredGameObject {
	public final GameObjectType type;
	public final Class<?> objectClass;

	public RegisteredGameObject(GameObjectType type, Class<?> gameObjectClass) {
		this.type = type;
		this.objectClass = gameObjectClass;
	}
}
