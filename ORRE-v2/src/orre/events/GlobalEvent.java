package orre.events;

public class GlobalEvent<EventParamsDataType> 
{
	private EventParamsDataType eventParameter = null;
	public final GlobalEventType eventType;
	
	public GlobalEvent(GlobalEventType eventType)
	{
		this.eventType = eventType;
	}
	public GlobalEvent(GlobalEventType enqueueStartupLoadingItem, EventParamsDataType eventParameterObject)
	{
		this.eventType = enqueueStartupLoadingItem;
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
	
	public synchronized GlobalEventType getEventType()
	{
		return this.eventType;
	}
}