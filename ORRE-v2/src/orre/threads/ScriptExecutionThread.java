package orre.threads;

import org.python.util.PythonInterpreter;

import orre.events.GlobalEventDispatcher;

public class ScriptExecutionThread extends Thread {
	private final PythonInterpreter interpreter;
	private final GlobalEventDispatcher eventDispatcher;

	public ScriptExecutionThread(GlobalEventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		this.interpreter = new PythonInterpreter();
	}
	
	public void run() {
		while(true) {
			
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
}
