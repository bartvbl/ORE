package orre.gameWorld.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import orre.api.PropertyTypeProvider;
import orre.util.Logger;
import orre.util.Logger.LogType;

public final class GameObject {
	public final int id;
	private static final AtomicInteger nextUID = new AtomicInteger();
	public final Enum<?> type;
	public final GameWorld world;
	private final ArrayList<Property> properties;
	private final ArrayList<Property> tickingProperties;
	private final ArrayList<Property> tickOnceProperties;
	private final ArrayList<GraphicsObject> controlledObjects;
	private final HashMap<Enum<?>, Object> propertyData;
	private final PropertyTypeProvider propertyTypeProvider;

	public GameObject(Enum<?> gameObjectType, PropertyTypeProvider propertyTypeProvider, GameWorld world) {
		this.id = nextUID.getAndIncrement();
		this.propertyData = new HashMap<Enum<?>, Object>();
		this.type = gameObjectType;
		this.world = world;
		this.properties = new ArrayList<Property>();
		this.tickingProperties = new ArrayList<Property>();
		this.tickOnceProperties = new ArrayList<Property>();
		this.controlledObjects = new ArrayList<GraphicsObject>();
		this.propertyTypeProvider = propertyTypeProvider;
	}
	
	public void addProperty(Property property) {
		if(!properties.contains(property)) {			
			this.properties.add(property);
			if(property.requiresFastTick) {
				this.tickingProperties.add(property);
			}
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
		if(!tickOnceProperties.isEmpty()) {
			tickOnceProperties.remove(0).tick();
		}
		if(tickingProperties.size() > 0) {
			for(Property property : tickingProperties) {
				property.tick();
			}
		}
	}

	public void handleMessage(Message<?> message) {
		for(Property property : properties) {
			property.handleMessage(message);
		}
	}
	
	public void tickOnce(Property property) {
		this.tickOnceProperties.add(property);
	}
	
	public GraphicsObject getGraphicsObject(int index) {
		return controlledObjects.get(index);
	}
	
	public int getGraphicsObjectCount() {
		return controlledObjects.size();
	}

	public Object requestPropertyData(Enum<?> propertyDataType, Class<?> expectedDataType) {
		if(!propertyData.containsKey(propertyDataType)) {
			Logger.log("Property data " + propertyDataType + " not found in object " + this.id, LogType.WARNING);
			return null;
		}
		Object data = propertyData.get(propertyDataType);
		if(!expectedDataType.isAssignableFrom(data.getClass())) {
			throw new RuntimeException("Property data type " + propertyDataType + " was not the same as the expected type (" + data.getClass() + " versus " + expectedDataType + ")");
		}
		return data;
	}
	
	public void setPropertyData(Enum<?> propertyDataType, Object value) {
		if(!propertyTypeProvider.getRequiredDataType(propertyDataType).isInstance(value)) {
			throw new RuntimeException("The property data type " + propertyDataType + " requires a value of data type " + propertyTypeProvider.getRequiredDataType(propertyDataType) + " and received one of type " + value);
		}
		this.propertyData.put(propertyDataType, value);
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

	public Enum<?>[] getAttachedPropertyTypes() {
		ArrayList<Enum<?>> propertyTypes = new ArrayList<Enum<?>>();
		for(Property property : properties) {
			propertyTypes.add(property.type);
		}
		return propertyTypes.toArray(new Enum<?>[propertyTypes.size()]);
	}

	public void initProperties() {
		for(Property property : properties) {
			property.init();
		}
	}
}
