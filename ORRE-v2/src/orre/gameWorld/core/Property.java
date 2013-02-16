package orre.gameWorld.core;

public abstract class Property {
	protected final PropertyType type;

	public Property (PropertyType type) {
		this.type = type;
	}

	public void handleMessage(Message message) {
		
	}

	public void tick() {
		
	}

	public void destroy() {
		
	}
}
