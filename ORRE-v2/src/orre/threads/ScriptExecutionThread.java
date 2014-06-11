package orre.threads;

import org.python.util.PythonInterpreter;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gameWorld.core.GameWorld;
import orre.scripting.ScriptAPI;
import orre.util.Queue;

public class ScriptExecutionThread extends Thread {
	private final PythonInterpreter interpreter;
	private final Queue<String> executionQueue = new Queue<String>();
	
	public ScriptExecutionThread() {
		this.interpreter = new PythonInterpreter();
	}
	
	public void run() {
		interpreter.execfile(GameMain.class.getClassLoader().getResourceAsStream("init.py"));
		while(true) {
			synchronized(executionQueue) {
				while(!executionQueue.isEmpty()) {
					String pythonSource = executionQueue.dequeue();
					interpreter.exec(pythonSource);
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
}
