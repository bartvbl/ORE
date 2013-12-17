package orre.gameWorld.services;

import orre.ai.core.AICommand;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameWorld;

public class AssignTaskCommand extends AICommand {

	private final int targetID;
	private final TaskType[] acceptableTaskTypes;

	public AssignTaskCommand(int id, TaskType[] acceptableTaskTypes) {
		this.targetID = id;
		this.acceptableTaskTypes = acceptableTaskTypes;
	}

	@Override
	public void execute(GameWorld world, TaskMaster taskMaster) {
		Task task = taskMaster.assignTask(targetID, acceptableTaskTypes);
		world.dispatchMessage(new NewTaskMessage(task), targetID);
	}

}
