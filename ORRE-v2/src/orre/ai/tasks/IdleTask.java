package orre.ai.tasks;

import orre.geom.Point2D;

public class IdleTask extends Task {

	public IdleTask(int gameObjectID) {
		super(TaskType.IDLE, gameObjectID);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public Point2D getLocation(Point2D taskExecutorLocation) {
		return taskExecutorLocation;
	}

	@Override
	public boolean isExecutionPossible() {
		return true;
	}

	@Override
	public void plan(Point2D locationOnMap) {
		//no need
	}

}
