package openrr.test;

public class Event<EventParamsDataType> {
	private final EventParamsDataType eventParameter;
	public final EventType eventType;

	public Event(EventType eventType) {
		this.eventType = eventType;
		this.eventParameter = null;
	}

	public Event(EventType eventType, EventParamsDataType eventParameterObject) {
		this.eventType = eventType;
		this.eventParameter = eventParameterObject;
	}

	public EventParamsDataType getEventParameterObject() {
		return this.eventParameter;
	}

	public boolean hasParameterObject() {
		if (this.eventParameter != null) {
			return true;
		} else {
			return false;
		}
	}
}