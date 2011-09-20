package orre.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import orre.modules.Module;

public class ThreadManager {
	private Module[] moduleList;
	
	private ArrayList<WorkerThread> workerThreadList = new ArrayList<WorkerThread>();
	private Stack<WorkerThread> idleThreads = new Stack<WorkerThread>();
	
	private ModuleCue moduleCue;
	
	public ThreadManager(Module[] moduleList)
	{
		this.moduleList = moduleList;
	}
	
	public void initialize()
	{
		moduleCue = new ModuleCue(this.moduleList);
		int numberOfThreads = this.getNumberOfAvailableCPUCores();
		WorkerThread thread;
		System.out.println("spawning " + numberOfThreads + " worker threads");
		for(int i = 0; i < numberOfThreads; i++)
		{
			thread = new WorkerThread(this);
			this.workerThreadList.add(thread);
			thread.start();
		}
	}
	
	public synchronized void tick() {
		this.moduleCue.reset();
		this.idleThreads = new Stack<WorkerThread>();
		for(WorkerThread i:this.workerThreadList)
		{
			i.notify();
		}
	}
	
	public synchronized void pauseThread(WorkerThread thread)
	{
		try {
			thread.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Module GetNextModuleForExecution(WorkerThread thread)
	{
		if(moduleCue.cueIsFinished())
		{
			
		}
		Module nextModule = this.moduleCue.getNextModuleForExecution();
		if(nextModule == null)
		{
			this.idleThreads.add(thread);
		}
		return nextModule;
	}
	
	public synchronized void markModuleAsExecuted(int moduleID)
	{
		this.moduleCue.markModuleAsCompleted(moduleID);
	}
	
	private int getNumberOfAvailableCPUCores()
	{
		return Runtime.getRuntime().availableProcessors();
	}
}
