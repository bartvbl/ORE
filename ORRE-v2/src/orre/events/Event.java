package orre.events;

public class Event<EventParamsDataType> 
{
	private EventParamsDataType eventParameter = null;
	private String eventType;
	
	public Event(String eventType)
	{
		this.eventType = eventType;
	}
	public Event(String eventType, EventParamsDataType eventParameterObject)
	{
		this.eventType = eventType;
		this.eventParameter = eventParameterObject;
	}
	
	public EventParamsDataType getEventParameterObject()
	{
		return this.eventParameter;
	}
	public boolean hasParameterObject()
	{
		if(this.eventParameter != null)
		{
			return true;
		} else {
			return false;
		}
	}
	public String getEventType()
	{
		return this.eventType;
	}
}