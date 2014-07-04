package orre.gameWorld.services;

import orre.ai.commands.AssignTaskCommand;
import orre.ai.commands.RegisterPendingCommand;
import orre.ai.commands.ReturnTaskCommand;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskRequest;
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

	public void assignTask(TaskRequest request) {
		this.aiThread.enqueueTask(new AssignTaskCommand(request));
	}

	public void stop() {
		this.aiThread.stopExecution();
	}

	public void returnTask(Task task) {
		this.aiThread.enqueueTask(new ReturnTaskCommand(task));
	}
}