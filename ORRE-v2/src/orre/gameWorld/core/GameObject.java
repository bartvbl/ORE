package orre.gameWorld.core;

import java.util.ArrayList;

public final class GameObject {
	public final int id;
	private static int nextUID = 0;
	public final GameObjectType type;
	public final GameWorld world;
	private final ArrayList<Property> properties;
	private final ArrayList<GraphicsObject> controlledObjects;

	public GameObject(GameObjectType type, GameWorld world) {
		this.id = nextUID;
		nextUID++;
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

	public void handleMessage(Message message) {
		for(Property property : properties) {
			property.handleMessage(message);
		}
	}

	public Object requestPropertyData(RequestedDataType type) {
		for(Property property : properties) {
			Object returnedData = property.handlePropertyDataRequest(type);
			if(returnedData != null)
			{
				return returnedData;
			}
		}
		return null;
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
}
