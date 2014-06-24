package orre.scripting;

import orre.events.EventHandler;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameWorld.core.GameWorld;
import orre.threads.ScriptExecutionThread;
import orre.util.ConcurrentQueue;

public class ScriptAPI implements EventHandler {
	private static GameWorld currentGameWorld;
	private static final ConcurrentQueue<Runnable> runOnMainThreadQueue = new ConcurrentQueue<Runnable>();
	private static GUIScriptHandler guiHandler;

	public ScriptAPI(GlobalEventDispatcher eventDispatcher) {
		eventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
		guiHandler = new GUIScriptHandler();
	}

	public static void spawn(final String gameObjectType) {
		runOnMainThread(new Runnable(){
			@Override
			public void run() {
				currentGameWorld.api_spawnGameObjectFromString(gameObjectType);
			}
		});
	}
	
	public static void load() {
		
	}
	
	public static void runOnMainThread(Runnable runnable) {
		runOnMainThreadQueue.enqueue(runnable);
	}

	@Override
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType == GlobalEventType.CHANGE_GAME_STATE) {
			
		}
	}

	public static void setCurrentWorld(GameWorld world) {
		currentGameWorld = world;
		guiHandler.setCurrentWorld(world);
	}

	//run just a single command to avoid command spam
	public static void runMainThreadCommand() {
		if(!runOnMainThreadQueue.isEmpty()) {
			runOnMainThreadQueue.dequeue().run();
		}
	}
}
