package openrr.test.guiHandlers;

import openrr.test.Event;
import openrr.test.EventType;
import openrr.test.EventHandler;

public class DefaultTestHandler implements EventHandler{
	
	private EventType lookoutEvent;
	
	public DefaultTestHandler(EventType inEvent) {
		lookoutEvent = inEvent;
	}
	
	public void handleEvent(Event<?> event) {
		if (lookoutEvent==event.eventType) {
			System.out.println(event+" dispatched and caught!");
		}
	}

}
