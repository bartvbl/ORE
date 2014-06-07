package orre.gameWorld.core;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import orre.util.Logger;
import orre.util.Logger.LogType;

public class GameObjectBuilder {
	
	private static final HashMap<GameObjectType, PropertyType[]> gameObjectTypes = new HashMap<GameObjectType, PropertyType[]>();
	private static final HashMap<PropertyType, Class<? extends Property>> propertyTypes = new HashMap<PropertyType, Class<? extends Property>>();

	public static GameObject buildGameObjectByType(GameObjectType type, GameWorld gameWorld) {
		GameObject gameObject = new GameObject(type, gameWorld);
		PropertyType[] propertyTypes = type.properties;
		for(PropertyType propertyType : propertyTypes) {
			Property property = createPropertyByType(propertyType, gameObject);
			gameObject.addProperty(property);
			property.init();
		}
		return gameObject;
	}

	private static Property createPropertyByType(PropertyType propertyType, GameObject gameObject) {
		try {
			Property createdProperty = (Property) propertyType.propertyClass.getConstructors()[0].newInstance(gameObject);
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
		Logger.log("Tried to build an object of type " + gameObject.type + ". Failed to create property " + propertyType, LogType.ERROR);
		return null;
	}

	public static void registerGameObjectType(GameObjectType type, PropertyType[] propertyTypes) {
		gameObjectTypes.put(type, propertyTypes);
	}
	
	public static void registerPropertyType(PropertyType type, Class<? extends Property> propertyClass) {
		propertyTypes.put(type, propertyClass);
	}

}
