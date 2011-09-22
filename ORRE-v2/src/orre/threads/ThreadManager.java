package orre.threads;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

import orre.modules.Module;

public class ThreadManager {
	private ArrayList<WorkerThread> workerThreadList = new ArrayList<WorkerThread>();
	private ArrayList<ContinuousThread> continuousThreadList = new ArrayList<ContinuousThread>();
	
	private CyclicBarrier dispatched_frameStartBarrier;
	private CyclicBarrier dispatched_frameEndBarrier;
	private int barrierSize;
	
	private boolean threadsRunning = false;
	
	private MainThreadExecutor mainThreadExecutor;
	
	public ThreadManager(ArrayList<Module> mainLoopModuleCue)
	{
		this.mainThreadExecutor = new MainThreadExecutor(mainLoopModuleCue);
		this.dispatched_frameEndBarrier = new CyclicBarrier(1);
		this.dispatched_frameStartBarrier = new CyclicBarrier(1);
		this.barrierSize = 1;
	}
	
	public void startThreads()
	{
		this.threadsRunning = true;
		for(WorkerThread thread : this.workerThreadList)
		{
			thread.run();
		}
		for(ContinuousThread thread : this.continuousThreadList)
		{
			thread.run();
		}
	}
	
	public void tick()
	{
		this.mainThreadExecutor.run();
	}
	
	public void shutDown()
	{
		for(WorkerThread thread : this.workerThreadList)
		{
			thread.shutdown();
		}
		for(ContinuousThread thread : this.continuousThreadList)
		{
			thread.shutdown();
		}
	}
	
	public void createSyncedWorkerThread(ArrayList<Module> moduleList)
	{
		if(this.threadsRunning)
		{
			System.out.println("you can not create new threads while the threads are running!");
			return;
		}
		WorkerThread thread = new WorkerThread(moduleList);
		this.workerThreadList.add(thread);
		this.updateBarriers(this.barrierSize + 1);
	}
	
	public void createContinuousThread(ArrayList<Module> moduleList, long minimumSleepTime)
	{
		if(this.threadsRunning)
		{
			System.out.println("you can not create new threads while the threads are running!");
			return;
		}
		ContinuousThread thread = new ContinuousThread(moduleList, minimumSleepTime);
		this.continuousThreadList.add(thread);
	}
	
	private synchronized void updateBarriers(int barrierSize)
	{
		synchronized(this.dispatched_frameEndBarrier)
		{
			this.dispatched_frameEndBarrier = new CyclicBarrier(barrierSize);
		}
		synchronized(this.dispatched_frameStartBarrier)
		{
			this.dispatched_frameStartBarrier = new CyclicBarrier(barrierSize);
		}
		this.barrierSize = barrierSize;
		for(WorkerThread thread : this.workerThreadList)
		{
			thread.updateBarrierReferences(this.dispatched_frameStartBarrier, this.dispatched_frameEndBarrier);
		}
	}
	
	public static int getNumberOfAvailableCPUCores()
	{
		return Runtime.getRuntime().availableProcessors();
	}
}
