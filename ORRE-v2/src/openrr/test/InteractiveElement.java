package openrr.test;

public class InteractiveElement extends GUIElement {
	
	public InteractiveElement(int[] posData, EventDispatcher eventDispatcher, Frame parent) {
		super(posData, eventDispatcher, parent);
	}
	
	public void addEventListener(EventType eventType, EventHandler eventHandler) {
		eventDispatcher.addEventListener(this, eventType, eventHandler);
	}
	
	public void onMouseMouse() { }
	
	public void onMousePress() { }
	
	public void onMouseRelease() { }
}
