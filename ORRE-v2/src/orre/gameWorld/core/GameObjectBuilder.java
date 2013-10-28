package orre.gameWorld.core;

import orre.gameWorld.properties.Flashlight;
import orre.gameWorld.properties.GravityProperty;
import orre.gameWorld.properties.HealthProperty;
import orre.gameWorld.properties.RockRaiderAppearance;
import orre.input.KeyboardCameraController;

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
			case KEYBOARD_CAMERA_CONTROLLER: return new KeyboardCameraController(gameObject);
			case LIGHT: return new Flashlight(gameObject);
			case GRAVITY: return new GravityProperty(gameObject);
			case ROCK_RAIDER_APPEARANCE: return new RockRaiderAppearance(gameObject);
		default:
			break;
		}
		return null;
	}

}
