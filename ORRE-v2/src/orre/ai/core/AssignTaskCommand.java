package orre.ai.core;

import orre.ai.tasks.Assignment;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskRequest;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.messages.NewAssignmentMessage;
import orre.util.ConcurrentQueue;

public class AssignTaskCommand extends AICommand {

	private final TaskRequest request;

	public AssignTaskCommand(TaskRequest request) {
		this.request = request;
	}

	@Override
	public void execute(final GameWorld world, TaskMaster taskMaster, ConcurrentQueue<Runnable> mainThreadQueue) {
		final Assignment task = taskMaster.assignTask(request);
		mainThreadQueue.enqueue(new Runnable(){
			@Override
			public void run() {
				world.dispatchMessage(new NewAssignmentMessage(task), request.targetID);
			}
		});
	}

}
