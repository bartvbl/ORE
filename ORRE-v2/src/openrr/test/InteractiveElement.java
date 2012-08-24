package openrr.test;

public class InteractiveElement extends GUIElement {
	
	protected EventDispatcher eventDispatcher;
	
	public InteractiveElement(int[] posData, EventDispatcher inEventDispatcher, Frame parent) {
		super(posData, parent);
		eventDispatcher = inEventDispatcher;
	}
	
	public void addEventListener(EventType eventType, EventHandler eventHandler) {
		eventDispatcher.addEventListener(this, eventType, eventHandler);
	}
	
	public void onMouseMouse() { }
	
	public void onMousePress() { }
	
	public void onMouseRelease() { }
	
	public EventDispatcher getEventDispatcher() {
		return eventDispatcher;
	}
}
