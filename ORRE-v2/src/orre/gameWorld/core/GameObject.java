package orre.gameWorld.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import orre.util.Logger;
import orre.util.Logger.LogType;

public final class GameObject {
	public final int id;
	private static final AtomicInteger nextUID = new AtomicInteger();
	public final GameObjectType type;
	public final GameWorld world;
	private final ArrayList<Property> properties;
	private final ArrayList<GraphicsObject> controlledObjects;
	private final HashMap<PropertyType, HashMap<PropertyDataType, Object>> propertyData;

	public GameObject(GameObjectType type, GameWorld world) {
		this.id = nextUID.getAndIncrement();
		this.propertyData = new HashMap<PropertyType, HashMap<PropertyDataType, Object>>();
		this.type = type;
		this.world = world;
		this.properties = new ArrayList<Property>();
		this.controlledObjects = new ArrayList<GraphicsObject>();
	}
	
	public void addProperty(Property property) {
		if(!properties.contains(property)) {			
			this.properties.add(property);
			this.propertyData.put(property.type, new HashMap<PropertyDataType, Object>());
		}
	}
	
	public void removeProperty(Property property) {
		properties.remove(property);
	}

	public void destroy() {
		for(Property property : properties) {
			property.destroy();
		}
	}

	public void tick() {
		for(Property property : properties) {
			property.tick();
		}
	}

	public void handleMessage(Message<?> message) {
		for(Property property : properties) {
			property.handleMessage(message);
		}
	}
	
	public GraphicsObject getGraphicsObject(int index) {
		return controlledObjects.get(index);
	}
	
	public int getGraphicsObjectCount() {
		return controlledObjects.size();
	}

	public Object requestPropertyData(PropertyType propertyType, PropertyDataType type, Class<?> expectedDataType) {
		Object data = propertyData.get(propertyType).get(type);
		if(data == null) {
			Logger.log("Property data " + type + " not found in object " + this.id, LogType.WARNING);
			return null;
		}
		if(!expectedDataType.isAssignableFrom(data.getClass())) {
			throw new RuntimeException("Property data type " + type + " was not the same as the expected type (" + data.getClass() + " versus " + expectedDataType + ")");
		}
		return data;
	}
	
	public void setPropertyData(PropertyType propertyType, PropertyDataType type, Object value) {
		if(!type.expectedReturnDataType.isInstance(value)) {
			throw new RuntimeException("The property data type " + type + " requires a value of data type " + type.expectedReturnDataType + " and received one of type " + value);
		}
		Logger.log("Setting property data: " + propertyType + " -> " + type + " -> " + value, LogType.MESSAGE);
		this.propertyData.get(propertyType).put(type, value);
	}
	
	public void takeControl(GraphicsObject object) {
		this.controlledObjects.add(object);
		for(Property property : properties) {
			property.handleMessage(new Message<GraphicsObject>(MessageType.OBJECT_CONTROL_GAINED, object));
		}
	}
	
	public void releaseControl(GraphicsObject object) {
		boolean objectWasRemoved = this.controlledObjects.remove(object);
		if(objectWasRemoved) {
			for(Property property : properties) {
				property.handleMessage(new Message<GraphicsObject>(MessageType.OBJECT_CONTROL_RELEASED, object));
			}
		}
	}

	public ArrayList<Property> debugonly_getAllProperties() {
		return properties;
	}
}
