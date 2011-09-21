package orre.threads;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import orre.modules.Module;

public class MainThreadExecutor {
	private CyclicBarrier frameStartBarrier;
	private CyclicBarrier frameEndBarrier;
	
	private final ModuleCue moduleCue;
	
	public MainThreadExecutor(ArrayList<Module> moduleCue)
	{
		this.moduleCue = new ModuleCue(moduleCue);
	}
	
	public void run()
	{
		this.moduleCue.reset();
		this.waitForFrameStart();
		this.executeModuleCue();
		this.waitForFrameEnd();
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
