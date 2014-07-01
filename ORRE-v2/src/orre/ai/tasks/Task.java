package orre.ai.tasks;

import orre.geom.Point2D;

/** A single job that has to be carried out in the world, without any information how this can be accomplished. */
public abstract class Task {
	public final Enum<?> type;
	public final int gameObjectID;
	
	public Task(Enum<?> type, int gameObjectID) {
		this.type = type;
		this.gameObjectID = gameObjectID;
	}
	
	public abstract boolean isExecutionPossible();
	public abstract void update();
	public abstract boolean isFinished();
	public abstract void plan(Point2D locationOnMap);
	public abstract double getPlanCost();
}
