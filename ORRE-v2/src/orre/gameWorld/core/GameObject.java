package orre.gameWorld.core;

public final class GameObject {
	public final int id;
	public final GameObjectType type;

	public GameObject(int UID, GameObjectType type) {
		this.id = UID;
		this.type = type;
	}
	
	public void addProperty(Property property) {
		
	}
	
	public void removeProperty() {
		
	}

	public void destroy() {
		
	}

	public void tick() {
		
	}

	public void handleMessage(Message message) {
		
	}
}
