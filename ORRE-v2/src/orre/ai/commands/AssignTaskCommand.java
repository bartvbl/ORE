package orre.ai.commands;

import orre.ai.tasks.Assignment;
import orre.ai.tasks.Plan;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskRequest;
import orre.ai.tasks.TaskSupplier;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.messages.NewAssignmentMessage;
import orre.util.ConcurrentQueue;

public class AssignTaskCommand extends AICommand {

	private final TaskRequest request;

	public AssignTaskCommand(TaskRequest request) {
		this.request = request;
	}

	@Override
	public void execute(final GameWorld world, TaskSupplier supplier, ConcurrentQueue<Runnable> mainThreadQueue) {
		final Assignment assignment = supplier.assignTask(request);
		mainThreadQueue.enqueue(new Runnable(){
			@Override
			public void run() {
				world.dispatchMessage(new NewAssignmentMessage(assignment), request.targetID);
			}
		});
	}

}
