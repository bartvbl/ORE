package orre.gameWorld.services;

import orre.ai.core.AIThread;
import orre.ai.core.RegisterPendingCommand;
import orre.ai.tasks.PendingTask;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;

public class AIService implements Service {
	
	private final AIThread aiThread;
	
	public AIService(GameWorld world) {
		this.aiThread = new AIThread(world);
		aiThread.start();
	}

	public void tick() {
		
	}

	public void registerTask(PendingTask task) {
		this.aiThread.enqueueTask(new RegisterPendingCommand(task));
	}

	public void assignTask(int id, TaskType[] acceptableTaskTypes) {
		this.aiThread.enqueueTask(new AssignTaskCommand(id, acceptableTaskTypes));
	}
}