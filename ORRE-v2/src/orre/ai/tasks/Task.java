package orre.ai.tasks;

import orre.animation.Animation;

public class Task {
	public final TaskType type;
	public final Animation execution;
	
	public Task(TaskType type, Animation execution) {
		this.type = type;
		this.execution = execution;
	}
}
