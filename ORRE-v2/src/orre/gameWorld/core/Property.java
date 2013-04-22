package orre.gameWorld.core;

public abstract class Property {
	protected final PropertyType type;

	public Property (PropertyType type) {
		this.type = type;
	}

	public abstract void handleMessage(Message<?> message);

	public abstract void tick();

	public abstract void destroy();

	public abstract Object handlePropertyDataRequest(RequestedDataType type);
}
