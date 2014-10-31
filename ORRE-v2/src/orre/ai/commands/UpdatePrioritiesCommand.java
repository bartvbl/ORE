package orre.ai.commands;

import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskSupplier;
import orre.gameWorld.core.GameWorld;
import orre.util.ConcurrentQueue;

public class UpdatePrioritiesCommand extends AICommand{

	private final Enum<?>[] priorities;

	public UpdatePrioritiesCommand(Enum<?>[] taskTypeArray) {
		this.priorities = taskTypeArray;
	}

	@Override
	public void execute(GameWorld world, TaskSupplier supplier, ConcurrentQueue<Runnable> mainThreadQueue) {
		supplier.updatePriorities(priorities);
	}
}

