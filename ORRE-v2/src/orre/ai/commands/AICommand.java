package orre.ai.commands;

import orre.ai.tasks.TaskSupplier;
import orre.gameWorld.core.GameWorld;
import orre.util.ConcurrentQueue;

public abstract class AICommand {
	public abstract void execute(GameWorld world, TaskSupplier supplier, ConcurrentQueue<Runnable> mainThreadQueue);

}
