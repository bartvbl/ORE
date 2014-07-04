package orre.ai.commands;

import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.gameWorld.core.GameWorld;
import orre.util.ConcurrentQueue;

public class ReturnTaskCommand extends AICommand {

	private final Task task;

	public ReturnTaskCommand(Task task) {
		this.task = task;
	}

	@Override
	public void execute(GameWorld world, TaskMaster taskMaster, ConcurrentQueue<Runnable> mainThreadQueue) {
		taskMaster.returnTask(task);
	}

}
