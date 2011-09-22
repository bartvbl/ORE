package orre.events;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import orre.modules.Module;

public class EventDispatcher {
	private ConcurrentHashMap<String, ArrayList<Module>> listeners = new ConcurrentHashMap<String, ArrayList<Module>>();
	private ConcurrentHashMap<String, Stack<Event<?>>> dispatchedEventCue = new ConcurrentHashMap<String, Stack<Event<?>>>();
	
	public EventDispatcher()
	{
		
	}
	public synchronized void addEventListener(String type, Module module)
	{
		
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
