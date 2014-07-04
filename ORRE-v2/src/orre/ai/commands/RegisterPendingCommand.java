package orre.ai.commands;

import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.gameWorld.core.GameWorld;
import orre.util.ConcurrentQueue;

public class RegisterPendingCommand extends AICommand {

	private final Task pendingTask;

	public RegisterPendingCommand(Task task) {
		this.pendingTask = task;
	}

	@Override
	public void execute(GameWorld world, TaskMaster taskMaster, ConcurrentQueue<Runnable> mainThread) {
		taskMaster.registerPendingTask(pendingTask);
	}

}
