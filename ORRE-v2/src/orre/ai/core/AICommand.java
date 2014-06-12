package orre.ai.core;

import orre.ai.tasks.TaskMaster;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.util.ConcurrentQueue;
import orre.util.Queue;

public abstract class AICommand {
	public abstract void execute(GameWorld world, TaskMaster taskMaster, ConcurrentQueue<Runnable> mainThreadQueue);

}
