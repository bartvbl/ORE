package orre.gameWorld.services;

import orre.ai.tasks.PendingTask;
import orre.ai.tasks.TaskMaster;
import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameObjectType;
import orre.gameWorld.core.GameWorld;

public class AIService implements Service {
	private final TaskMaster taskMaster;
	
	public AIService(GameWorld world) {
		this.taskMaster = new TaskMaster(world);
	}

	public void tick() {
		
	}

	public void registerTransportable(GameObjectType type, int id) {
		if(type == GameObjectType.ORE) {
			taskMaster.registerPendingTask(new PendingTask(TaskType.COLLECT_ORE, id));
		} else if(type == GameObjectType.CHRYSTAL) {
			taskMaster.registerPendingTask(new PendingTask(TaskType.COLLECT_CHRYSTAL, id));
		}
	}
}
