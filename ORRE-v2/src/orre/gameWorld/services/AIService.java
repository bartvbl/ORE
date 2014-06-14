package orre.gameWorld.services;

import orre.ai.core.AssignTaskCommand;
import orre.ai.core.RegisterPendingCommand;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameWorld;
import orre.geom.Point2D;
import orre.threads.AIThread;

public class AIService implements Service {
	
	private final AIThread aiThread;
	
	public AIService(GameWorld world) {
		this.aiThread = new AIThread(world);
		aiThread.start();
	}

	@Override
	public void tick() {
		this.aiThread.executeMainThreadTasks();
	}

	public void registerTask(Task task) {
		this.aiThread.enqueueTask(new RegisterPendingCommand(task));
	}

	public void assignTask(int id, TaskType[] acceptableTaskTypes, Point2D locationOnMap) {
		this.aiThread.enqueueTask(new AssignTaskCommand(id, acceptableTaskTypes, locationOnMap));
	}

	public void stop() {
		this.aiThread.stopExecution();
	}
}