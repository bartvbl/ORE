package orre.threads;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import orre.modules.Module;

public class WorkerThread extends Thread {
	private boolean running = false;
	private CyclicBarrier frameStartBarrier;
	private CyclicBarrier frameEndBarrier;
	
	private ModuleCue moduleCue;
	
	public WorkerThread(ArrayList<Module> moduleCue)
	{
		this.moduleCue = new ModuleCue(moduleCue);
		this.setName("game worker thread " + this.getId());
	}
	
	public void updateBarrierReferences(CyclicBarrier frameStartBarrier, CyclicBarrier frameEndBarrier)
	{
		this.frameStartBarrier = frameStartBarrier;
		this.frameEndBarrier = frameEndBarrier;
	}
	
	public void run()
	{
		running = true;
		while(running)
		{
			this.waitForFrameStart();
			this.executeModuleCue();
			this.waitForFrameEnd();
			this.moduleCue.reset();
		}
	}

	private void waitForFrameStart() {
		try {
			this.frameStartBarrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
	private void waitForFrameEnd() {
		try {
			this.frameEndBarrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
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
