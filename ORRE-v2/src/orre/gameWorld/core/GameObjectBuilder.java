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
			Property createdProperty = (Property) propertyType.propertyClass.getConstructors()[0].newInstance(gameObject);
			createdProperty.init();
			return createdProperty;
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
		System.out.println("Tried to build an object of type " + gameObject.type + ". Failed to create property " + propertyType);
		return null;
	}

}
