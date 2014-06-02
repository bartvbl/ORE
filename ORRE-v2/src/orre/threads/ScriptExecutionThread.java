package orre.threads;

import org.python.util.PythonInterpreter;

import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameWorld;

public class ScriptExecutionThread extends Thread {
	private final PythonInterpreter interpreter;
	private final GlobalEventDispatcher eventDispatcher;
	
	private boolean hasUpdatedWorld = false;
	private GameWorld currentWorld = null;

	public ScriptExecutionThread(GlobalEventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		this.interpreter = new PythonInterpreter();
	}
	
	public void run() {
		while(true) {
			if(hasUpdatedWorld) {
				interpreter.set("ORRE_world", currentWorld);
				hasUpdatedWorld = false;
			}
			sleep();
		}
	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void execute(String pythonScript) {
		
	}

	public void setCurrentWorld(GameWorld world) {
		this.currentWorld  = world;
		this.hasUpdatedWorld = true;
	}
}
