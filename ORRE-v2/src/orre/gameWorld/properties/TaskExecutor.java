package orre.gameWorld.properties;

import orre.ai.tasks.IdleTask;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskType;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.core.PropertyType;
import orre.gameWorld.messages.NewTaskMessage;
import orre.geom.Point2D;
import orre.geom.Point3D;
import orre.geom.mesh.Mesh3D;

public class TaskExecutor extends Property {
	private Task currentTask;
	private boolean hasTask = false;

	public TaskExecutor(GameObject gameObject) {
		super(PropertyType.TASK_EXECUTOR, gameObject);
		this.currentTask = new IdleTask(gameObject.id);
	}

	@Override
	public void handleMessage(Message<?> message) {
		if(message instanceof NewTaskMessage) {
			NewTaskMessage newTask = (NewTaskMessage) message;
			this.currentTask = newTask.getPayload();
			hasTask = true;
			System.out.println("Received new task: " + currentTask);
		}
	}

	@Override
	public void tick() {
		if(currentTask.isFinished() || !hasTask) {
			Mesh3D appearance = (Mesh3D) gameObject.requestPropertyData(this.type, PropertyDataType.APPEARANCE, Mesh3D.class);
			Point3D location = appearance.root.getLocation();
			Point2D location2D = location.in2D();
			this.gameObject.world.services.aiService.assignTask(this.gameObject.id, new TaskType[]{TaskType.COLLECT_ORE}, location2D);
			hasTask = true;
		} else if(hasTask) {
			currentTask.update();
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() {
		
	}

}
