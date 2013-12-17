package orre.ai.core;

import orre.ai.tasks.TaskMaster;
import orre.gameWorld.core.GameWorld;

public abstract class AICommand {

	public abstract void execute(GameWorld world, TaskMaster taskMaster);

}
