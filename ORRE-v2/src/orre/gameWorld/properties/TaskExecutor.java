package orre.gameWorld.properties;

import orre.ai.tasks.IdleTask;
import orre.ai.tasks.Task;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.messages.NewTaskMessage;
import orre.geom.Point2D;
import orre.geom.Point3D;
import orre.geom.mesh.Model;

public abstract class TaskExecutor extends Property {
	private Task currentTask;
	private boolean hasTask = false;

	private final Enum<?>[] assignableTaskTypes;

	public TaskExecutor(GameObject gameObject, Enum<?> propertyType, Enum<?>[] assignableTaskTypes) {
		super(propertyType, gameObject);
		this.currentTask = new IdleTask(gameObject.id);
		this.assignableTaskTypes = assignableTaskTypes;
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
			Model appearance = (Model) gameObject.requestPropertyData(PropertyDataType.APPEARANCE, Model.class);
			Point3D location = appearance.getRootNode().getLocation();
			Point2D location2D = location.in2D();
			this.gameObject.world.services.aiService.assignTask(this.gameObject.id, assignableTaskTypes, location2D);
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
