package orre.modules;

import java.util.ArrayList;
import java.util.HashMap;

import orre.events.Event;
import orre.events.ConcurrentEventDispatcher;
import orre.events.EventType;
import orre.scene.Scene;

import org.lwjgl.input.Keyboard;

public class Input extends Module{
	private ArrayList<Integer> registeredKeyList = new ArrayList<Integer>();
	private HashMap<Integer, Boolean> keyStates = new HashMap<Integer, Boolean>();
	private HashMap<Integer, Integer> numberOfListeners = new HashMap<Integer, Integer>();
	
	public Input(ConcurrentEventDispatcher eventDispatcher, Scene sceneGraph) {
		super(eventDispatcher, sceneGraph);
	}
	
	public void execute()
	{
		System.out.println("module update: input");
		this.handleEvents();
		
		boolean keyIsDown;
		for(int keyNum : this.registeredKeyList)
		{
			keyIsDown = Keyboard.isKeyDown(keyNum);
			if(this.keyStates.get(keyNum) != keyIsDown)
			{
				this.keyStates.put(keyNum, keyIsDown);
				if(keyIsDown == true)
				{
					this.eventDispatcher.dispatchEvent(new Event<Integer>(EventType.INPUT_KEY_PRESSED, keyNum));
				} else {
					this.eventDispatcher.dispatchEvent(new Event<Integer>(EventType.INPUT_KEY_RELEASED, keyNum));
				}
			}
		}
	}
	public void initialize()
	{
		this.eventDispatcher.addEventListener(EventType.INPUT_KEY_REGISTERED, this);
		this.eventDispatcher.addEventListener(EventType.INPUT_KEY_UNREGISTERED, this);
	}
	public void onStateEnter()
	{
		
	}
	public void onStateRelease()
	{
		
	}
	
	private void handleEvents()
	{
		ArrayList<Event<?>> eventCue = this.eventDispatcher.getEventsByListenerModule(this);
		for(Event<?> currentEvent : eventCue)
		{
			if(currentEvent.getEventType().equals(EventType.INPUT_KEY_REGISTERED))
			{
				this.registerKeyIfNotRegistered(currentEvent);
			}
			if(currentEvent.getEventType().equals(EventType.INPUT_KEY_UNREGISTERED))
			{
				this.unregisterKey(currentEvent);
			}
		}
	}
	
	private void registerKeyIfNotRegistered(Event<?> event)
	{
		if(!isValidKeyEvent(event))
		{
			return;
		}
		int keyToRegister = (Integer)event.getEventParameterObject();
		if(!this.registeredKeyList.contains(keyToRegister))
		{
			this.registeredKeyList.add(keyToRegister);
			this.keyStates.put(keyToRegister, Keyboard.isKeyDown(keyToRegister));
			this.numberOfListeners.put(keyToRegister, 1);
		}
	}
	
	private void unregisterKey(Event<?> event)
	{
		if(!isValidKeyEvent(event))
		{
			return;
		}
		int keyToUnregister = (Integer)event.getEventParameterObject();
		if(!this.registeredKeyList.contains(keyToUnregister))
		{
			int numListeners = this.numberOfListeners.get(keyToUnregister);
			if((numListeners - 1) == 0)
			{
				this.registeredKeyList.remove(keyToUnregister);
				this.keyStates.remove(keyToUnregister);
				this.numberOfListeners.remove(keyToUnregister);
			} else {
				this.numberOfListeners.put(keyToUnregister, numListeners - 1);
			}
			
			
		}
	}
	
	private boolean isValidKeyEvent(Event<?> event)
	{
		if(!event.hasParameterObject())
		{
			System.out.println("ERROR: a register key event must specify a key!");
			return false;
		}
		if(!(event.getEventParameterObject() instanceof Integer))
		{
			System.out.println("ERROR: a register key event must have a parameter of type integer.");
			return false;
		}
		return true;
	}
}
