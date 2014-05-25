package orre.gameWorld.core;

import java.util.ArrayList;
import java.util.HashMap;

public final class GameObject {
	public final int id;
	private static int nextUID = 0;
	public final GameObjectType type;
	public final GameWorld world;
	private final ArrayList<Property> properties;
	private final ArrayList<GraphicsObject> controlledObjects;
	private final HashMap<PropertyDataType, Object> propertyData;

	public GameObject(GameObjectType type, GameWorld world) {
		this.id = nextUID;
		nextUID++;
		this.propertyData = new HashMap<PropertyDataType, Object>();
		this.type = type;
		this.world = world;
		this.properties = new ArrayList<Property>();
		this.controlledObjects = new ArrayList<GraphicsObject>();
	}
	
	public void addProperty(Property property) {
		if(!properties.contains(property)) {			
			this.properties.add(property);
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

	public Object requestPropertyData(PropertyDataType type) {
		return propertyData.get(type);
	}
	
	public void setPropertyData(PropertyDataType type, Object value) {
		if(!type.expectedReturnDataType.isInstance(value)) {
			throw new RuntimeException("The property data type " + type + " requires a value of data type " + type.expectedReturnDataType + " and received one of type " + value);
		}
		this.propertyData.put(type, value);
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
