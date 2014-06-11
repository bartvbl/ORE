package orre.scripting;

import orre.events.Event;
import orre.events.EventHandler;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameWorld.core.GameWorld;
import orre.threads.ScriptExecutionThread;

public class ScriptInterpreter implements EventHandler {
	private static ScriptInterpreter instance;
	private final ScriptExecutionThread scriptThread;

	private ScriptInterpreter(GlobalEventDispatcher globalEventDispatcher) {
		this.scriptThread = new ScriptExecutionThread();
		globalEventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
	}
	
	public static ScriptInterpreter create(GlobalEventDispatcher eventDispatcher) {
		instance = new ScriptInterpreter(eventDispatcher);
		return instance;
	}
	
	public static ScriptInterpreter get() {
		return instance;
	}

	public void init() {
		scriptThread.start();
	}

	public void setCurrentWorld(GameWorld world) {
		ScriptAPI.setCurrentWorld(world);
	}
	
	public void execute(String pythonCode) {
		this.scriptThread.execute(pythonCode);
	}
	
	public void dispatchScriptEvent(String type, String parameters) {
		this.scriptThread.execute("orre_handleEvent('" + type + "', '"+parameters+"')");
	}

	@Override
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType == GlobalEventType.CHANGE_GAME_STATE) {
			this.dispatchScriptEvent("gameStateChanged", event.getEventParameterObject().toString());
		}
	}
	
}
