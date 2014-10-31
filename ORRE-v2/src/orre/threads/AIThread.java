package orre.threads;

import orre.ai.commands.AICommand;
import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskPriorities;
import orre.ai.tasks.TaskSupplier;
import orre.ai.tasks.TaskTracker;
import orre.gameWorld.core.GameWorld;
import orre.util.ConcurrentQueue;
import orre.util.Queue;

public class AIThread extends Thread {
	private final Queue<AICommand> taskQueue = new Queue<AICommand>();
	private final ConcurrentQueue<Runnable> mainThreadQueue = new ConcurrentQueue<Runnable>();
	private final TaskSupplier taskSupplier;
	private GameWorld world;
	private boolean isRunning = true;
	
	public AIThread(GameWorld world) {
		this.taskSupplier = new TaskSupplier(world);
		this.world = world;
	}
	
	public void enqueueTask(AICommand task) {
		synchronized(taskQueue) {
			taskQueue.enqueue(task);
		}
	}
	
	@Override
	public void run() {
		while(isRunning) {
			if(!taskQueue.isEmpty()) {
				AICommand task;
				synchronized(taskQueue) {
					task = taskQueue.dequeue();
				}
				task.execute(world, taskSupplier, mainThreadQueue);
			} else {
				try {
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void stopExecution() {
		isRunning = false;
	}

	public void executeMainThreadTasks() {
		while(!this.mainThreadQueue.isEmpty()) {
			mainThreadQueue.dequeue().run();
		}
	}
}
