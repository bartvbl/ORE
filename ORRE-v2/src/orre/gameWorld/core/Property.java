package orre.gameWorld.core;

public abstract class Property {
	public final PropertyType type;
	protected final GameObject gameObject;

	public Property (PropertyType type, GameObject gameObject) {
		this.type = type;
		this.gameObject = gameObject;
	}

	public abstract void handleMessage(Message<?> message);

	public abstract void tick();

	public abstract void destroy();
}
