package orre.events;

public class Event<EventParamsDataType> 
{
	private EventParamsDataType eventParameter = null;
	public final EventType eventType;
	
	public Event(EventType eventType)
	{
		this.eventType = eventType;
	}
	public Event(EventType type, EventParamsDataType eventParameterObject)
	{
		this.eventType = type;
		this.eventParameter = eventParameterObject;
	}
	
	
	public boolean hasParameterObject()
	{
		if(this.getEventParameterObject() != null)
		{
			return true;
		} else {
			return false;
		}
	}
	
	public synchronized EventParamsDataType getEventParameterObject()
	{
		return this.eventParameter;
	}
	
	public synchronized EventType getEventType()
	{
		return this.eventType;
	}
}