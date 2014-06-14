package orre.scripting;

import orre.events.EventHandler;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameWorld.core.GameWorld;
import orre.threads.ScriptExecutionThread;
import orre.util.ConcurrentQueue;

public class ScriptAPI implements EventHandler {
	private static ScriptExecutionThread executionThread;
	private static GameWorld currentGameWorld;
	private static final ConcurrentQueue<Runnable> runOnMainThreadQueue = new ConcurrentQueue<Runnable>();

	public ScriptAPI(GlobalEventDispatcher eventDispatcher, ScriptExecutionThread scriptExecutionThread) {
		eventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
		executionThread = scriptExecutionThread;
	}

	public static void spawn(final String gameObjectType) {
		runOnMainThreadQueue.enqueue(new Runnable(){
			@Override
			public void run() {
				currentGameWorld.api_spawnGameObjectFromString(gameObjectType);
			}
		});
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

	//run just a single command to avoid command spam
	public static void runMainThreadCommand() {
		if(!runOnMainThreadQueue.isEmpty()) {
			runOnMainThreadQueue.dequeue().run();
		}
	}
}
