package orre.gameWorld.core;

import java.lang.reflect.InvocationTargetException;

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
		try {
			return (Property) propertyType.propertyClass.getConstructors()[0].newInstance(gameObject);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

}
