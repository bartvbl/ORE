package orre.ai.core;

import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.messages.NewTaskMessage;
import orre.geom.Point2D;

public class AssignTaskCommand extends AICommand {

	private final int targetID;
	private final TaskType[] acceptableTaskTypes;
	private final Point2D locationOnMap;

	public AssignTaskCommand(int id, TaskType[] acceptableTaskTypes, Point2D locationOnMap) {
		this.targetID = id;
		this.acceptableTaskTypes = acceptableTaskTypes;
		this.locationOnMap = locationOnMap;
	}

	@Override
	public void execute(GameWorld world, TaskMaster taskMaster) {
		Task task = taskMaster.assignTask(targetID, acceptableTaskTypes, locationOnMap);
		world.dispatchMessage(new NewTaskMessage(task), targetID);
	}

}
