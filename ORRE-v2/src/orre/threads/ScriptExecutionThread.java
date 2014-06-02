package orre.threads;

import org.python.util.PythonInterpreter;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameWorld;
import orre.scripting.ScriptAPI;
import orre.util.Queue;

public class ScriptExecutionThread extends Thread {
	private final PythonInterpreter interpreter;
	private final GlobalEventDispatcher eventDispatcher;
	private final Queue<String> executionQueue = new Queue<String>();
	
	private boolean hasUpdatedWorld = false;
	private GameWorld currentWorld = null;

	public ScriptExecutionThread(GlobalEventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		this.interpreter = new PythonInterpreter();
		new ScriptAPI(eventDispatcher, this);
	}
	
	public void run() {
		interpreter.execfile(GameMain.class.getClassLoader().getResourceAsStream("init.py"));
		while(true) {
			if(hasUpdatedWorld) {
				interpreter.set("ORRE_world", currentWorld);
				hasUpdatedWorld = false;
			}
			
			synchronized(executionQueue) {
				while(!executionQueue.isEmpty()) {
					interpreter.exec(executionQueue.dequeue());
				}
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
		synchronized(executionQueue) {
			this.executionQueue.enqueue(pythonScript);
		}
	}

	public void setCurrentWorld(GameWorld world) {
		this.currentWorld  = world;
		this.hasUpdatedWorld = true;
	}
}
