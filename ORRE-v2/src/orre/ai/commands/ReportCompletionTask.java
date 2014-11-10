package orre.ai.commands;

import orre.ai.tasks.Task;
import orre.ai.tasks.TaskSupplier;
import orre.gameWorld.core.GameWorld;
import orre.util.ConcurrentQueue;

public class ReportCompletionTask extends AICommand {

	private final int gameObjectID;

	public ReportCompletionTask(int gameObjectID) {
		this.gameObjectID = gameObjectID;
	}

	@Override
	public void execute(GameWorld world, TaskSupplier supplier, ConcurrentQueue<Runnable> mainThreadQueue) {
		supplier.markTaskComplete(gameObjectID);
	}

}
