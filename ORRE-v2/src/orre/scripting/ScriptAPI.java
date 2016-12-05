package orre.scripting;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import org.python.core.PyDictionary;

import orre.events.EventHandler;
import orre.events.EventType;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.util.ConcurrentQueue;

public class ScriptAPI implements EventHandler {
	private static GameWorld currentGameWorld;
	private static final ConcurrentQueue<Runnable> runOnMainThreadQueue = new ConcurrentQueue<Runnable>();
	private static GUIScriptHandler guiHandler;
	private static AIScriptHandler aiScriptHandler;
	private static GlobalEventDispatcher eventDispatcher;

	public ScriptAPI(GlobalEventDispatcher eventDispatcher) {
		eventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
		guiHandler = new GUIScriptHandler();
		aiScriptHandler = new AIScriptHandler();
		ScriptAPI.eventDispatcher = eventDispatcher;
	}

	public static int spawn(final String gameObjectType) {
		final CyclicBarrier barrier = new CyclicBarrier(2);
		final AtomicInteger id = new AtomicInteger();

		runOnMainThread(new Runnable() {

			@Override
			public void run() {
				id.set(currentGameWorld.api_spawnGameObjectFromString(gameObjectType));
				try {
					 barrier.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
			
		});
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		return id.get();
	}
	
	public static void dispatchEvent(String eventType, PyDictionary parameters) {
		HashMap<String, String> parameterMap = new HashMap<String, String>();
		for(String key : (Set<String>) parameters.keySet()) {
			parameterMap.put(key, (String) parameters.get(key));
		}
		ScriptEvent event = new ScriptEvent(eventType, parameterMap);
		Message<ScriptEvent> message = new Message<ScriptEvent>(MessageType.SCRIPT_EVENT, event);
		currentGameWorld.dispatchMessage(message);
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
		aiScriptHandler.setCurrentWorld(world);
	}

	public static void runMainThreadCommand() {
		while(!runOnMainThreadQueue.isEmpty()) {
			runOnMainThreadQueue.dequeue().run();
		}
	}
}
