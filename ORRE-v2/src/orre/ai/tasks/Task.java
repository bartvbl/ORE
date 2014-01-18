package orre.ai.tasks;

import orre.geom.Point2D;

public abstract class Task {
	public final TaskType type;
	public final int gameObjectID;
	
	public Task(TaskType type, int gameObjectID) {
		this.type = type;
		this.gameObjectID = gameObjectID;
	}

	public abstract Point2D getLocation(Point2D taskExecutorLocation);
	public abstract void update();
	public abstract boolean isFinished();
}
