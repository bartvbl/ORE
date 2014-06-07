package orre.api;

public interface PropertyTypeProvider<GameObjectTypes extends Enum<?>, PropertyTypes extends Enum<?>, PropertyDataTypes extends Enum<?>> {
	public GameObjectTypes[] getGameObjectTypes();
	public PropertyTypes[] getProperties(GameObjectTypes gameObjectType);
	public Class<?> getPropertyClass(PropertyTypes type);
	public Class<?> getRequiredDataType(PropertyDataTypes dataType);
}
