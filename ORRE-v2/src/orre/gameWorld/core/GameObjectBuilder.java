package orre.gameWorld.core;

import orre.gameWorld.properties.HealthProperty;

public class GameObjectBuilder {

	public static GameObject buildGameObjectByType(GameObjectType type, GameWorld gameWorld) {
		GameObject gameObject = new GameObject(type, gameWorld);
		PropertyType[] propertyTypes = type.properties;
		for(PropertyType propertyType : propertyTypes) {
			Property property = createPropertyByType(propertyType);
			gameObject.addProperty(property);
		}
		return gameObject;
	}

	private static Property createPropertyByType(PropertyType propertyType) {
		switch(propertyType) {
			case HEALTH: return new HealthProperty();
			
		}
		return null;
	}

}
