package orre.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher {
	private final HashMap<GlobalEventType, ArrayList<EventHandler>> listeners = new HashMap<GlobalEventType, ArrayList<EventHandler>>();

	public void dispatchEvent(GlobalEvent<?> event) {
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

	public void addEventListener(EventHandler listenerModule, GlobalEventType eventType) {
		this.addEventTypeIfNotExistent(eventType);
		ArrayList<EventHandler> listenerList = this.listeners.get(eventType);
		listenerList.add(listenerModule);

	}

	private void addEventTypeIfNotExistent(GlobalEventType eventType) {
		if (!eventTypeExists(eventType)) {
			this.listeners.put(eventType, new ArrayList<EventHandler>());
		}
	}

	private boolean eventTypeExists(GlobalEventType eventType) {
		return this.listeners.containsKey(eventType);
	}
}