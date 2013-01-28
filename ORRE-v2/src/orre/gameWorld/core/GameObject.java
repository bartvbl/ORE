package orre.gameWorld.core;

import java.util.ArrayList;

import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public final class GameObject {
	public final int id;
	public final GameObjectType type;
	public final SceneNode objectRootNode;
	public final GameWorld world;
	private ArrayList<Property> properties;

	public GameObject(int UID, GameObjectType type, GameWorld world) {
		this.id = UID;
		this.type = type;
		this.world = world;
		this.properties = new ArrayList<Property>();
		this.objectRootNode = new EmptySceneNode();
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
}
