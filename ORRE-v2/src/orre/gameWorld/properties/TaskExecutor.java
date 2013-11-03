package orre.gameWorld.properties;

import orre.ai.tasks.Task;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyType;

public class TaskExecutor extends Property {
	private Task currentTask = null;

	public TaskExecutor(PropertyType type, GameObject gameObject) {
		super(type, gameObject);
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
