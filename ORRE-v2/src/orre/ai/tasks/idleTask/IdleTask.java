package orre.ai.tasks.idleTask;

import orre.ai.tasks.Action;
import orre.ai.tasks.Assignment;
import orre.ai.tasks.BasicTaskType;
import orre.ai.tasks.Plan;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskRequest;
import orre.geom.Point2D;

public class IdleTask extends Task {

	public IdleTask(int gameObjectID) {
		super(BasicTaskType.IDLE, gameObjectID);
	}

	@Override
	public Assignment plan(TaskRequest request) {
		return new Assignment(new Task[]{this}, new Plan(new Action[]{new IdleAction()}));
	}
}
