package orre.ai.tasks;

import orre.animation.Animation;

public class Task {
	public final TaskType type;
	public final Animation execution;
	public final int gameObjectID;
	
	public Task(TaskType type, Animation execution, int gameObjectID) {
		this.type = type;
		this.execution = execution;
		this.gameObjectID = gameObjectID;
	}
}
