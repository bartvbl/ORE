package orre.ai.tasks;

public abstract class Task {
	public final TaskType type;
	public final int gameObjectID;
	
	public Task(TaskType type, int gameObjectID) {
		this.type = type;
		this.gameObjectID = gameObjectID;
	}

	public abstract void update();
	public abstract boolean isFinished();
}
