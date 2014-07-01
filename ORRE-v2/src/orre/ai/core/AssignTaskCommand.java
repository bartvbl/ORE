package orre.ai.core;

import orre.ai.tasks.Task;
import orre.ai.tasks.TaskMaster;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.messages.NewTaskMessage;
import orre.geom.Point2D;
import orre.util.ConcurrentQueue;

public class AssignTaskCommand extends AICommand {

	private final int targetID;
	private final Enum<?>[] acceptableTaskTypes;
	private final Point2D locationOnMap;

	public AssignTaskCommand(int id, Enum<?>[] assignableTaskTypes, Point2D locationOnMap) {
		this.targetID = id;
		this.acceptableTaskTypes = assignableTaskTypes;
		this.locationOnMap = locationOnMap;
	}

	@Override
	public void execute(final GameWorld world, TaskMaster taskMaster, ConcurrentQueue<Runnable> mainThreadQueue) {
		final Task task = taskMaster.assignTask(targetID, acceptableTaskTypes, locationOnMap);
		mainThreadQueue.enqueue(new Runnable(){
			@Override
			public void run() {
				world.dispatchMessage(new NewTaskMessage(task), targetID);
			}
		});
	}

}
