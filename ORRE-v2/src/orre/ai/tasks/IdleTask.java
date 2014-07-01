package orre.ai.tasks;

import orre.geom.Point2D;

public class IdleTask extends Task {

	public IdleTask(int gameObjectID) {
		super(BasicTaskType.IDLE, gameObjectID);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isExecutionPossible() {
		return true;
	}

	@Override
	public void plan(Point2D locationOnMap) {
		//no need
	}

	@Override
	public double getPlanCost() {
		return 0;
	}

}
