package orre.ai.tasks;

import orre.geom.Point2D;

public class CollectOreTask extends Task {

	private final Point2D location;

	public CollectOreTask(int gameObjectID, Point2D oreLocation) {
		super(TaskType.COLLECT_ORE, gameObjectID);
		this.location = oreLocation;
	}

	@Override
	public boolean isExecutionPossible() {
		return true;
	}

	@Override
	public Point2D getLocation(Point2D taskExecutorLocation) {
		return location;
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void plan(Point2D locationOnMap) {
		
	}

}
