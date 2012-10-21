package openrr.test;

import java.util.ArrayList;
import java.util.HashMap;

public class Button extends InteractiveElement {
	
	protected String state;
	protected Enum buttonID;
	protected ArrayList<EventType> releaseEventTypes = new ArrayList<EventType>();
	protected HashMap<EventType, Object> releaseEventParameterObjects = new HashMap<EventType, Object>();
	
	public Button(int[] posData, String startState, String inButtonID, EventDispatcher eventDispatcher, Frame parent) {
		super(posData, eventDispatcher, parent);
		setState(state);
		buttonID = ButtonID.valueOf(inButtonID);
	}
	
	public void setState(String newState) {
		state = newState;
	}
	
	public String getState() {
		return state;
	}
	
	public void press() {
		setState("p");
	}
			
	public void hover() {
		setState("h");
	}
	
	public void release() {
		for (EventType eventType : releaseEventTypes) {
			getEventDispatcher().dispatchEvent(new Event(eventType, releaseEventParameterObjects.get(eventType)));
		}
		setState("n");
	}
	
	public Enum getButtonID() {
		return buttonID;
	}
	
	public void addReleaseEvent(EventType eventType) {
		releaseEventTypes.add(eventType);
		releaseEventParameterObjects.put(eventType, null);
	}
	
	public void addReleaseEvent(EventType eventType, Object parameter) {
		releaseEventTypes.add(eventType);
		releaseEventParameterObjects.put(eventType, parameter);
	}
}
