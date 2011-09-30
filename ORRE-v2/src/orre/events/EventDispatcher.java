package orre.events;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import orre.modules.Module;

public class EventDispatcher {
	private ConcurrentHashMap<String, CopyOnWriteArrayList<Module>> listeners = new ConcurrentHashMap<String, CopyOnWriteArrayList<Module>>();
	private ConcurrentHashMap<Module, ArrayList<Event<?>>> dispatchedEventCue = new ConcurrentHashMap<Module, ArrayList<Event<?>>>();
	
	public EventDispatcher()
	{
		
	}
	public synchronized void addEventListener(String type, Module module)
	{
		listeners.putIfAbsent(type, new CopyOnWriteArrayList<Module>());
		CopyOnWriteArrayList<Module> list = this.listeners.get(type);
		synchronized(list)
		{
			list.add(module);
		}
	}
	public synchronized void removeEventListener(String type, Module module)
	{
		if(!this.listeners.containsKey(type))
		{
			System.out.println("ERROR: tried to remove the event listener of type '" + type + "', which is not present in the dispatcher");
			return;
		}
		CopyOnWriteArrayList<Module> list = this.listeners.get(type);
		synchronized(list)
		{
			list.remove(module);
		}
	}
	public synchronized void dispatchEvent(Event<?> event)
	{
		CopyOnWriteArrayList<Module> listeners = this.listeners.get(event.getEventType());
		ArrayList<Event<?>> eventCue;
		for(Module module : listeners)
		{
			eventCue = this.dispatchedEventCue.get(module);
			synchronized(eventCue)
			{
				eventCue.add(event);
			}
		}
	}
	public synchronized ArrayList<Event<?>> GetEventsByEventType(Module listener)
	{
		ArrayList<Event<?>> eventCue = this.dispatchedEventCue.get(listener);
		
		synchronized(eventCue)
		{
			try{
				@SuppressWarnings("unchecked")
				ArrayList<Event<?>> returnedEventCue = (ArrayList<Event<?>>)eventCue.clone();
				eventCue.clear();
				return returnedEventCue;
			} catch(Exception e)
			{
				System.out.println("failed to cast the cloned event cue!");
				e.printStackTrace();
				
			}
		}
		return null;
	}
	
}
