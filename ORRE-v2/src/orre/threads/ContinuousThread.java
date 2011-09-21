package orre.threads;

import java.util.ArrayList;

import orre.modules.Module;

public class ContinuousThread extends Thread {
	private boolean running;
	private final ModuleCue moduleCue;
	private final long minimumSleepTime;
	
	public ContinuousThread(ArrayList<Module> moduleList, long minimumSleepTime)
	{
		this.moduleCue = new ModuleCue(moduleList);
		this.minimumSleepTime = minimumSleepTime;
	}
	public void run()
	{
		this.running = true;
		while(running)
		{
			this.executeModuleCue();
			this.sleep();
		}
	}
	
	private void sleep()
	{
		try {
			Thread.sleep(this.minimumSleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void shutdown()
	{
		running = false;
	}
	
	private void executeModuleCue()
	{
		Module currentModule = moduleCue.getNextModuleForExecution();
		while(currentModule != null)
		{
			currentModule.execute();
			currentModule = moduleCue.getNextModuleForExecution();
		}
	}
}
