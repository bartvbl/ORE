package orre.ai.core;

import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.gameWorld.core.GameWorld;

public class RegisterPendingCommand extends AICommand {

	private final Task pendingTask;

	public RegisterPendingCommand(Task task) {
		this.pendingTask = task;
	}

	@Override
	public void execute(GameWorld world, TaskMaster taskMaster) {
		System.out.println("registering new task: " + pendingTask);
		taskMaster.registerPendingTask(pendingTask);
	}

}
