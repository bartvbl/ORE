package orre.modules;


import java.util.Stack;

import orre.events.Event;

public class TaskCue { 
	private Stack<Event<?>> taskCue = new Stack<Event<?>>();
	
	public Event<?> getNext()
	{
		return taskCue.pop();
	}
	public void addEventToCue(Event<?> event)
	{
		this.taskCue.push(event);
	}
	public boolean cueIsEmpty()
	{
		return taskCue.isEmpty();
	}
}
