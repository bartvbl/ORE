package orre.ai.tasks;

import orre.geom.Point2D;

public class IdleTask extends Task {

	public IdleTask(int gameObjectID) {
		super(BasicTaskType.IDLE, gameObjectID);
	}

	@Override
	public Assignment plan(TaskRequest request) {
		return null;
	}
}
