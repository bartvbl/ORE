package orre.api;

import orre.gameWorld.core.Property;

public interface PropertyTypeProvider {
	//returns GameObjectType array
	public Enum<?>[] getGameObjectTypes();
	//returns a PropertyType array
	public Enum<?>[] getPropertyTypes();
	//returns PropertyType array, requires a GameObjectType value
	public Enum<?>[] getProperties(Enum<?> gameSpecificObjectType);
	//requires a PropertyType
	public Class<? extends Property> getPropertyClass(Enum<?> gameSpecificPropertyType);
	//Requires a PropertyDataType
	public Class<?> getRequiredDataType(Enum<?> gameSpecificDataType);
}
