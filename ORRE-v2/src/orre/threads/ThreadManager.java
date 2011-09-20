package orre.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import orre.modules.Module;
import orre.modules.TaskCue;

public class ThreadManager {
	private Module[] moduleList;
	
	private ArrayList<WorkerThread> workerThreadList = new ArrayList<WorkerThread>();
	private ArrayList<ContinuousThread> continuousThreadList = new ArrayList<ContinuousThread>();
	private Stack<WorkerThread> idleThreads = new Stack<WorkerThread>();
	
	private final ModuleCue moduleCue;
	
	public ThreadManager(Module[] moduleList)
	{
		this.moduleList = moduleList;
		moduleCue = new ModuleCue(this.moduleList);
	}
	
	public void run()
	{
		
	}
	
	public void createSyncedWorkerThread(Module[] moduleList)
	{
		WorkerThread thread = new WorkerThread(this);
		this.workerThreadList.add(thread);
		thread.start();
	}
	
	public void createContinuousThread(Module[] moduleList)
	{
		ContinuousThread thread = new ContinuousThread(this);
		this.continuousThreadList.add(thread);
		thread.start();
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
