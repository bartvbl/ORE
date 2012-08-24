package openrr.test.guiHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import openrr.test.InteractiveElement;
import openrr.test.EventHandler;
import openrr.test.Event;
import openrr.test.EventType;
import openrr.test.Button;

public class ButtonActionHandler implements EventHandler {
	
	Button button;
	
	public ButtonActionHandler(Button target) {
		target.addEventListener(EventType.MOUSE_MOVE, this);
		target.addEventListener(EventType.MOUSE_PRESS, this);
		target.addEventListener(EventType.MOUSE_RELEASE, this);
		button = target;
	}
	
	public void handleEvent(Event<?> event) {
		if (event.getEventParameterObject()==button) {
			if (event.eventType == EventType.MOUSE_MOVE) {
				button.hover();
			} else if (event.eventType == EventType.MOUSE_PRESS) {
				button.press();
			} else if (event.eventType == EventType.MOUSE_RELEASE) {
				button.release();
			}
		}
	}
	
	private void release() {
		for (EventType eventType : releaseEventTypes) {
			button.getEventDispatcher().dispatchEvent(new Event(eventType, releaseEventParameterObjects.get(eventType)));
		}
		button.setState("n");
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
