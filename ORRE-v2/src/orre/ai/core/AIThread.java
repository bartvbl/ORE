package orre.ai.core;

import org.lwjgl.opengl.Display;

import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskPriorities;
import orre.gameWorld.core.GameWorld;
import orre.util.Queue;

public class AIThread extends Thread {
	private final TaskMaster taskMaster;
	private final Queue<AICommand> taskQueue = new Queue<AICommand>();
	private GameWorld world;
	
	public AIThread(GameWorld world) {
		TaskPriorities priorities = new TaskPriorities();
		this.taskMaster = new TaskMaster(world, priorities);
		this.world = world;
	}
	
	public void enqueueTask(AICommand task) {
		synchronized(taskQueue) {
			taskQueue.enqueue(task);
		}
	}
	
	public void run() {
		while(!Display.isCloseRequested()) {
			if(!taskQueue.isEmpty()) {
				AICommand task;
				synchronized(taskQueue) {
					task = taskQueue.dequeue();
				}
				task.execute(world, taskMaster);
			} else {
				try {
					Thread.sleep(1000/60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
