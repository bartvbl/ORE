package orre.scripting;

import java.io.File;

import orre.events.EventHandler;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameWorld.core.GameWorld;
import orre.resources.ResourceType;
import orre.resources.UnloadedResource;
import orre.threads.ScriptExecutionThread;

public class ScriptAPI implements EventHandler {
	private static ScriptExecutionThread executionThread;
	private static GameWorld currentGameWorld;

	public ScriptAPI(GlobalEventDispatcher eventDispatcher, ScriptExecutionThread scriptExecutionThread) {
		eventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
		executionThread = scriptExecutionThread;
	}

	public static void spawn(String gameObjectType) {
		currentGameWorld.api_spawnGameObjectFromString(gameObjectType);
	}
	
	public void on() {
		
	}
	
	public static void load() {
		
	}

	@Override
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType == GlobalEventType.CHANGE_GAME_STATE) {
			
		}
	}

	public static void setCurrentWorld(GameWorld world) {
		currentGameWorld = world;
	}
	
	
}
