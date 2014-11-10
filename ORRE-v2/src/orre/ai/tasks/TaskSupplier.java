package orre.ai.tasks;

import java.util.ArrayList;

import orre.gameWorld.core.GameWorld;

public class TaskSupplier {

	private final TaskMaster taskMaster;
	private final TaskTracker taskTracker;

	public TaskSupplier(GameWorld world) {
		this.taskTracker = new TaskTracker();
		this.taskMaster = new TaskMaster(world, taskTracker);
	}

	public Assignment assignTask(TaskRequest request) {
		Assignment assignment = taskMaster.findAssignment(request);
		taskTracker.assign(assignment, request.targetID);
		return assignment;
	}

	public void registerPendingTask(Task pendingTask) {
		taskTracker.registerPendingTask(pendingTask);
	}

	public void updatePriorities(Enum<?>[] priorities) {
		taskMaster.updatePriorities(priorities);
	}

	public void markTaskComplete(int gameObjectID) {
		taskTracker.markCompleted(gameObjectID);
	}

	public void returnTask(int gameObjectID) {
		taskTracker.abort(gameObjectID);
	}

}
