package orre.ai.tasks;

import orre.gameWorld.core.GameObjectType;

public class PendingTask {

	public final TaskType taskType;
	public final int gameObjectID;
	public final GameObjectType gameObjectType;
	
	public PendingTask(TaskType type, int id, GameObjectType gameObjectType) {
		this.taskType = type;
		this.gameObjectID = id;
		this.gameObjectType = gameObjectType;
	}
}
