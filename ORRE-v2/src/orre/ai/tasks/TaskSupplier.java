package orre.ai.tasks;

import orre.gameWorld.core.GameWorld;

public class TaskSupplier {

	private final TaskMaster taskMaster;
	private final TaskTracker taskTracker;

	public TaskSupplier(GameWorld world) {
		this.taskMaster = new TaskMaster(world);
		this.taskTracker = new TaskTracker();
	}

	public Assignment assignTask(TaskRequest request) {
		Assignment assignment = taskMaster.findAssignment(request);
		taskTracker.assign(assignment, request.targetID);
		return assignment;
	}

	public void registerPendingTask(Task pendingTask) {
		taskMaster.registerPendingTask(pendingTask);
	}

	public void returnTask(Task[] task) {
		taskMaster.returnTask(task);
	}

	public void updatePriorities(Enum<?>[] priorities) {
		taskMaster.updatePriorities(priorities);
	}

}
