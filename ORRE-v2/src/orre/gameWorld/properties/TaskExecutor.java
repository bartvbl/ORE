package orre.gameWorld.properties;

import orre.ai.tasks.Task;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.core.PropertyType;
import orre.geom.mesh.Mesh3D;

public class TaskExecutor extends Property {
	private Task currentTask = null;

	public TaskExecutor(GameObject gameObject) {
		super(PropertyType.TASK_EXECUTOR, gameObject);
		this.gameObject.world.services.aiService.assignTask(gameObject.id, null);
		this.gameObject.world.services.animationService.applyAnimation(currentTask.execution, (Mesh3D) this.gameObject.requestPropertyData(PropertyDataType.APPEARANCE));
		
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
