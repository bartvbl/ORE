package orre.ai.tasks;

public class PendingTask {

	public final TaskType type;
	public final int gameObjectID;
	
	public PendingTask(TaskType type, int id) {
		this.type = type;
		this.gameObjectID = id;
	}

}
