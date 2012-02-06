package orre.events;

public class Event<EventParamsDataType> 
{
	private EventParamsDataType eventParameter = null;
	public final String eventType;
	
	public Event(String eventType)
	{
		this.eventType = eventType;
	}
	public Event(String eventType, EventParamsDataType eventParameterObject)
	{
		this.eventType = eventType;
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
	
	public synchronized String getEventType()
	{
		return this.eventType;
	}
}