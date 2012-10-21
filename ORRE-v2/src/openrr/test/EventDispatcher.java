package openrr.test;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher {
	//private final HashMap<EventType, ArrayList<EventHandler>> listeners = new HashMap<EventType, ArrayList<EventHandler>>();
	private final HashMap<EventType, HashMap<Object, ArrayList<EventHandler>>> listeners = new HashMap<EventType, HashMap<Object, ArrayList<EventHandler>>>();

	public void dispatchEvent(Event<?> event) {
		if (!eventTypeExists(event.eventType)) {
			System.err.println("WARNING: dispatch attempted of event with event type '" + event.eventType
					+ "', which has no listeners");
			return;
		} else {
			if (event.eventType != EventType.TIMER_TICK) {
				//System.out.println("dispatched event: " + event.eventType);
			}
		}

		//ArrayList<EventHandler> eventHandlersList = listeners.get(event.eventType);
		ArrayList<EventHandler> eventHandlersList = getHandlers(event);
		for (EventHandler i : eventHandlersList) {
			i.handleEvent(event);
		}
	}
	
	public ArrayList<EventHandler> getHandlers(Event event) {
		ArrayList<EventHandler> eventHandlersList = new ArrayList<EventHandler>();
		HashMap x = listeners.get(event.eventType);
		for (Object source : x.keySet()) {
			eventHandlersList.addAll((ArrayList<EventHandler>)x.get(source));
		}
		return eventHandlersList;
	}

	public void addEventListener(GUIElement source, EventType eventType, EventHandler eventHandler) {
		this.addEventTypeIfNotExistent(eventType, source);
		System.out.println(source+" "+listeners.get(eventType).get(source));
		ArrayList<EventHandler> listenerList = listeners.get(eventType).get(source);
		
		listenerList.add(eventHandler);
	}
	
	public void addEventListener(Object source, EventType eventType, EventHandler eventHandler) {
		this.addEventTypeIfNotExistent(eventType, source);
		ArrayList<EventHandler> listenerList = listeners.get(eventType).get(source);
		listenerList.add(eventHandler);
	}
	
	public void removeEventListener(EventType eventType, Object source) {
		listeners.get(eventType).remove(source);
	}
	
	public void removeEventHandler(EventType eventType, Object source, EventHandler eventHandler) {
		ArrayList<EventHandler> handlers = listeners.get(eventType).get(source);
		for (EventHandler handler : handlers) {
			if (handler == eventHandler) {
				handlers.remove(handler);
			}
		}
		listeners.get(eventType).put(source, handlers);
	}

	private void addEventTypeIfNotExistent(EventType eventType, Object source) {
		if (!eventTypeExists(eventType)) {
			listeners.put(eventType, new HashMap<Object, ArrayList<EventHandler>>());
		}
		if (!sourceIndexExists(source, listeners.get(eventType))) {
			listeners.get(eventType).put(source, new ArrayList<EventHandler>());
		}
	}

	private boolean eventTypeExists(EventType eventType) {
		return listeners.containsKey(eventType);
	}
	
	private boolean sourceIndexExists(Object source, HashMap sourceHandlers) {
		return sourceHandlers.containsKey(source);
	}
	
	public void printSourceHandlers(EventType eventType, Object source) {
		System.out.println(listeners.get(eventType).get(source));
	}
}