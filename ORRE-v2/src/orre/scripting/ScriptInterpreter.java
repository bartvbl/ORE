package orre.scripting;

import orre.events.GlobalEventDispatcher;
import orre.threads.ScriptExecutionThread;

public class ScriptInterpreter {

	private final ScriptExecutionThread scriptThread;

	public ScriptInterpreter(GlobalEventDispatcher globalEventDispatcher) {
		this.scriptThread = new ScriptExecutionThread(globalEventDispatcher);
	}

	public void init() {
		scriptThread.start();
	}
	
}
