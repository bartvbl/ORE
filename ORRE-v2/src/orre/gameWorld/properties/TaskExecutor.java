package orre.gameWorld.properties;

import orre.ai.tasks.Assignment;
import orre.ai.tasks.IdleTask;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskRequest;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.messages.NewAssignmentMessage;
import orre.geom.Point2D;
import orre.geom.Point3D;
import orre.geom.mesh.Model;

public abstract class TaskExecutor extends Property {
	private Assignment currentAssignment;
	private boolean hasTask = false;

	protected final Enum<?>[] assignableTaskTypes;

	public TaskExecutor(GameObject gameObject, Enum<?> propertyType, Enum<?>[] assignableTaskTypes) {
		super(propertyType, gameObject);
		this.assignableTaskTypes = assignableTaskTypes;
	}

	@Override
	public void handleMessage(Message<?> message) {
		if(message instanceof NewAssignmentMessage) {
			NewAssignmentMessage newTask = (NewAssignmentMessage) message;
			this.currentAssignment = newTask.getPayload();
			hasTask = true;
			System.out.println("Received new task: " + currentAssignment);
		}
	}

	@Override
	public void tick() {
		if(currentAssignment.plan.isFinished() || !hasTask) {
			TaskRequest request = generateTaskRequest();
			this.gameObject.world.services.aiService.assignTask(request);
			hasTask = true;
		} else if(hasTask) {
			currentAssignment.plan.update();
		}
	}

	protected abstract TaskRequest generateTaskRequest();

	@Override
	public void destroy() {

	}

	@Override
	public void init() {
		
	}

}
