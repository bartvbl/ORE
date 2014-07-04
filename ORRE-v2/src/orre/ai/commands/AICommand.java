package orre.ai.commands;

import orre.ai.tasks.TaskMaster;
import orre.gameWorld.core.GameWorld;
import orre.util.ConcurrentQueue;

public abstract class AICommand {
	public abstract void execute(GameWorld world, TaskMaster taskMaster, ConcurrentQueue<Runnable> mainThreadQueue);

}
