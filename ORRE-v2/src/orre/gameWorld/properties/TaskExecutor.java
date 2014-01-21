package orre.gameWorld.properties;

import orre.ai.tasks.IdleTask;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.core.PropertyType;
import orre.geom.Point2D;
import orre.geom.Point3D;
import orre.geom.mesh.Mesh3D;

public class TaskExecutor extends Property {
	private Task currentTask;

	public TaskExecutor(GameObject gameObject) {
		super(PropertyType.TASK_EXECUTOR, gameObject);
		this.currentTask = new IdleTask(gameObject.id);
	}

	@Override
	public void handleMessage(Message<?> message) {
		System.out.println(message);
	}

	@Override
	public void tick() {
		if(currentTask.isFinished()) {
			Mesh3D appearance = (Mesh3D) gameObject.requestPropertyData(PropertyDataType.APPEARANCE);
			Point3D location = appearance.root.getLocation();
			Point2D location2D = location.in2D();
			this.gameObject.world.services.aiService.assignTask(this.gameObject.id, new TaskType[]{TaskType.COLLECT_ORE}, location2D);
		}
	}

	@Override
	public void destroy() {

	}

}
