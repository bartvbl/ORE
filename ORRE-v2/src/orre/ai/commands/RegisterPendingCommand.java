package orre.ai.commands;

import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskSupplier;
import orre.gameWorld.core.GameWorld;
import orre.util.ConcurrentQueue;

public class RegisterPendingCommand extends AICommand {

	private final Task pendingTask;

	public RegisterPendingCommand(Task task) {
		this.pendingTask = task;
	}

	@Override
	public void execute(GameWorld world, TaskSupplier supplier, ConcurrentQueue<Runnable> mainThreadQueue) {
		supplier.registerPendingTask(pendingTask);
	}

}
