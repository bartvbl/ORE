package orre.ai.tasks;

import orre.animation.Animation;
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

}
