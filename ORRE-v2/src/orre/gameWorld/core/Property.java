package orre.gameWorld.core;

public abstract class Property {
	public final Enum<?> type;
	public final boolean requiresFastTick;
	protected final GameObject gameObject;

	public Property (Enum<?> type, GameObject gameObject) {
		this(type, gameObject, false);
	}
	
	public Property (Enum<?> type, GameObject gameObject, boolean enableFastTick) {
		this.type = type;
		this.gameObject = gameObject;
		this.requiresFastTick = enableFastTick;
	}

	public abstract void handleMessage(Message<?> message);

	public abstract void tick();

	public abstract void destroy();

	public abstract void init();
}
