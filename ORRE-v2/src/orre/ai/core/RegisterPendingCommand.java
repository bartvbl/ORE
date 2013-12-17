package orre.ai.core;

import orre.ai.tasks.PendingTask;
import orre.ai.tasks.TaskMaster;
import orre.gameWorld.core.GameWorld;

public class RegisterPendingCommand extends AICommand {

	private final PendingTask pendingTask;

	public RegisterPendingCommand(PendingTask task) {
		this.pendingTask = task;
	}

	@Override
	public void execute(GameWorld world, TaskMaster taskMaster) {
		taskMaster.registerPendingTask(pendingTask);
	}

}
