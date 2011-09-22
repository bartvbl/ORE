package orre.events;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import orre.modules.Module;

public class EventDispatcher {
	private ConcurrentHashMap<String, ArrayList<Module>> listeners = new ConcurrentHashMap<String, ArrayList<Module>>();
	private ConcurrentHashMap<Module, Stack<Event<?>>> dispatchedEventCue = new ConcurrentHashMap<Module, Stack<Event<?>>>();
	
	public EventDispatcher()
	{
		
	}
	public synchronized void addEventListener(String type, Module module)
	{
		listeners.putIfAbsent(type, new ArrayList<Module>());
		ArrayList<Module> list = this.listeners.get(type);
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
		ArrayList<Module> list = this.listeners.get(type);
		synchronized(list)
		{
			list.remove(module);
		}
	}
	public synchronized void dispatchEvent(Event<?> event)
	{
		
	}
	public synchronized void dispatchTo(Event<?> event, Module module)
	{
		
	}
	public synchronized void GetEventsByEventType(String eventType)
	{
		
	}
	
}
