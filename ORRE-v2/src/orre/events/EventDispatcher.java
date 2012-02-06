package orre.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher {
	private final HashMap<String, ArrayList<EventHandler>> listeners = new HashMap<String, ArrayList<EventHandler>>();

	public void dispatchEvent(Event<?> event) {
		if (!eventTypeExists(event.eventType)) {
			System.out.println("WARNING: dispatch attempted of event with event type '" + event.eventType
					+ "', which has no listeners");
			return;
		} else {
			System.out.println("dispatched event: " + event.eventType);
		}

		ArrayList<EventHandler> eventHandlersList = this.listeners.get(event.eventType);
		for (EventHandler i : eventHandlersList) {
			i.handleEvent(event);
		}
	}

	public void addEventListener(EventHandler listenerModule, String eventType) {
		this.addEventTypeIfNotExistent(eventType);
		ArrayList<EventHandler> listenerList = this.listeners.get(eventType);
		listenerList.add(listenerModule);

	}

	private void addEventTypeIfNotExistent(String eventType) {
		if (!eventTypeExists(eventType)) {
			this.listeners.put(eventType, new ArrayList<EventHandler>());
		}
	}

	private boolean eventTypeExists(String eventType) {
		return this.listeners.containsKey(eventType);
	}
}