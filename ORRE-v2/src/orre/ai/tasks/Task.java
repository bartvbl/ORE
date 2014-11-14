package orre.ai.tasks;

import orre.gameWorld.core.GameWorld;

/** A single job that has to be carried out in the world, without any information how this can be accomplished. */
public abstract class Task {
	public final Enum<?> type;
	public final int gameObjectID;
	
	public Task(Enum<?> type, int gameObjectID) {
		this.type = type;
		this.gameObjectID = gameObjectID;
	}
	
	public abstract Assignment plan(TaskRequest request, TaskMaster taskMaster, GameWorld world);
}
