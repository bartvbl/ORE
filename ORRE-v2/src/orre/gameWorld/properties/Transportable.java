package orre.gameWorld.properties;

import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;

public class Transportable extends Property {

	public Transportable(GameObject gameObject) {
		super(PropertyType.TRANSPORTABLE, gameObject);
		//gameObject.world.services.aiService.registerTask(new PendingTask(TaskType.COLLECT, gameObject.id, gameObject.type));
	}

	@Override
	public void handleMessage(Message<?> message) {
		
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void destroy() {
		
	}
}
