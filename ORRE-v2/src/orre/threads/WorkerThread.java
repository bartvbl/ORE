package orre.threads;

import orre.modules.Module;

public class WorkerThread extends Thread {
	private boolean running = false;
	private ThreadManager threadManager;
	
	public WorkerThread(ThreadManager manager)
	{
		this.threadManager = manager;
	}
	
	public void run()
	{
		Module currentExecutingModule;
		running = true;
		while(running)
		{
			System.out.println("working.. (" + this.getName() + ")");
			currentExecutingModule = threadManager.GetNextModuleForExecution(this);
			if(currentExecutingModule != null)
			{
				currentExecutingModule.execute();
				this.threadManager.markModuleAsExecuted(currentExecutingModule.getModuleID());
			} else {
				this.waitForNextTask();
				System.out.println("going on happily");
			}
		}
	}
	
	private synchronized void waitForNextTask() {
		this.threadManager.pauseThread(this);
	}

	public synchronized void terminate()
	{
		running = false;
	}
	
}
