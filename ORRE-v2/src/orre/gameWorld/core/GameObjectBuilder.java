package orre.gameWorld.core;

import orre.gameWorld.properties.KeyboardCameraController;
import orre.gameWorld.properties.HealthProperty;

public class GameObjectBuilder {

	public static GameObject buildGameObjectByType(GameObjectType type, GameWorld gameWorld) {
		GameObject gameObject = new GameObject(type, gameWorld);
		PropertyType[] propertyTypes = type.properties;
		for(PropertyType propertyType : propertyTypes) {
			Property property = createPropertyByType(propertyType, gameObject);
			gameObject.addProperty(property);
		}
		return gameObject;
	}

	private static Property createPropertyByType(PropertyType propertyType, GameObject gameObject) {
		switch(propertyType) {
			case HEALTH: return new HealthProperty(gameObject);
			case CAMERA_CONTROLLER: return new KeyboardCameraController(gameObject);
		}
		return null;
	}

}
