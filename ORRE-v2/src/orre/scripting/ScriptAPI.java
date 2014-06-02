package orre.scripting;

import orre.events.EventHandler;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.threads.ScriptExecutionThread;

public class ScriptAPI implements EventHandler {
	private final ScriptExecutionThread executionThread;

	public ScriptAPI(GlobalEventDispatcher eventDispatcher, ScriptExecutionThread scriptExecutionThread) {
		eventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
		this.executionThread = scriptExecutionThread;
	}

	public static void spawn() {
		
	}
	
	public void on() {
		
	}

	@Override
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType == GlobalEventType.CHANGE_GAME_STATE) {
			
		}
	}
	
	
}
