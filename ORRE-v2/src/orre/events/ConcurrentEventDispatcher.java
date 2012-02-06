package orre.events;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import orre.modules.Module;

public class ConcurrentEventDispatcher {
	private ConcurrentHashMap<String, CopyOnWriteArrayList<Module>> listeners = new ConcurrentHashMap<String, CopyOnWriteArrayList<Module>>();
	private ConcurrentHashMap<Module, AtomicReference<ArrayList<Event<?>>>> dispatchedEventCue = new ConcurrentHashMap<Module, AtomicReference<ArrayList<Event<?>>>>();
	
	public ConcurrentEventDispatcher()
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
		AtomicReference<ArrayList<Event<?>>> eventCueReference;
		ArrayList<Event<?>> eventCue;
		for(Module module : listeners)
		{
			eventCueReference = this.dispatchedEventCue.get(module);
			eventCue = eventCueReference.get();
			synchronized(eventCue)
			{
				eventCue.add(event);
			}
		}
	}
	public synchronized ArrayList<Event<?>> getEventsByListenerModule(Module listener)
	{
		AtomicReference<ArrayList<Event<?>>> eventCueReference = this.dispatchedEventCue.get(listener);
		ArrayList<Event<?>> emptyList = new ArrayList<Event<?>>();
		ArrayList<Event<?>> returnedEventCue = eventCueReference.getAndSet(emptyList);
		return returnedEventCue;
	}
	
}
