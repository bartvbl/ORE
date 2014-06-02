package orre.scripting;

import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameWorld;
import orre.threads.ScriptExecutionThread;

public class ScriptInterpreter {
	private final ScriptExecutionThread scriptThread;

	public ScriptInterpreter(GlobalEventDispatcher globalEventDispatcher) {
		this.scriptThread = new ScriptExecutionThread(globalEventDispatcher);
	}

	public void init() {
		scriptThread.start();
	}

	public void setCurrentWorld(GameWorld world) {
		this.scriptThread.setCurrentWorld(world);
	}
	
}
