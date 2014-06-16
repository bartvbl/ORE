package orre.gameWorld.core;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import orre.api.PropertyTypeProvider;
import orre.util.Logger;
import orre.util.Logger.LogType;

public class GameObjectBuilder {
	
	//mapping of GameObjectType -> PropertyType
	private static final HashMap<Enum<?>, Enum<?>[]> gameObjectTypes = new HashMap<Enum<?>, Enum<?>[]>();
	//mapping of PropertyType -> Class
	private static final HashMap<Enum<?>, Class<? extends Property>> propertyTypes = new HashMap<Enum<?>, Class<? extends Property>>();
	
	private static PropertyTypeProvider propertyTypeProvider;

	public static GameObject buildGameObjectByType(Enum<?> gameObjectType, GameWorld gameWorld) {
		GameObject gameObject = new GameObject(gameObjectType, propertyTypeProvider, gameWorld);
		Enum<?>[] propertyTypes = gameObjectTypes.get(gameObjectType);
		for(Enum<?> propertyType : propertyTypes) {
			Property property = createPropertyByType(propertyType, gameObject);
			gameObject.addProperty(property);
		}
		gameObject.initProperties();
		return gameObject;
	}

	private static Property createPropertyByType(Enum<?> propertyType, GameObject gameObject) {
		try {
			Property createdProperty = (Property) propertyTypes.get(propertyType).getConstructors()[0].newInstance(gameObject);
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

	public static void setPropertyTypeProvider(PropertyTypeProvider provider) {
		populateGameObjectTypeMap(provider);
		populatePropertyTypeMap(provider);
		propertyTypeProvider = provider;
	}

	private static void populateGameObjectTypeMap(PropertyTypeProvider provider) {
		gameObjectTypes.clear();
		for(GameObjectType baseType : GameObjectType.values()) {
			gameObjectTypes.put(baseType, baseType.properties);
		}
		Enum<?>[] extendedObjectTypes = provider.getGameObjectTypes();
		for(Enum<?> gameSpecificObjectType : extendedObjectTypes) {
			gameObjectTypes.put(gameSpecificObjectType, provider.getProperties(gameSpecificObjectType));
		}
	}
	
	private static void populatePropertyTypeMap(PropertyTypeProvider provider) {
		propertyTypes.clear();
		for(PropertyType baseType : PropertyType.values()) {
			propertyTypes.put(baseType, baseType.propertyClass);
		}
		Enum<?>[] gameSpecificPropertyTypes = provider.getPropertyTypes();
		for(Enum<?> gameSpecificProperty : gameSpecificPropertyTypes) {
			propertyTypes.put(gameSpecificProperty, provider.getPropertyClass(gameSpecificProperty));
		}
	}

	public static Enum<?> getGameObjectTypeFromString(String gameObjectType) {
		try {
			return GameObjectType.valueOf(gameObjectType);
		} catch(Exception e) {
			return propertyTypeProvider.getGameObjectTypeFromString(gameObjectType);
		}
	}

}
