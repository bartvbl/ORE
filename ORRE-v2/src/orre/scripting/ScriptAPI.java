package orre.scripting;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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
	private static AIScriptHandler aiScriptHandler;

	public ScriptAPI(GlobalEventDispatcher eventDispatcher) {
		eventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
		guiHandler = new GUIScriptHandler();
		aiScriptHandler = new AIScriptHandler();
	}
	
	private static class Spawner implements Runnable {
		public Spawner(String gameObjectType, CyclicBarrier barrier) {
			this.barrier = barrier;
			this.gameObjectType = gameObjectType;
		}
		
		private String gameObjectType;
		private final CyclicBarrier barrier;
		int id;
		
		@Override
		public void run() {
			id = currentGameWorld.api_spawnGameObjectFromString(gameObjectType);
			try {
				 barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

	public static int spawn(final String gameObjectType) {
		final CyclicBarrier barrier = new CyclicBarrier(2);
		final Spawner spawner = new Spawner(gameObjectType, barrier);

		runOnMainThread(spawner);
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		return spawner.id;
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
