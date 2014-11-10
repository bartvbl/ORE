package orre.gameWorld.properties;

import orre.ai.tasks.Assignment;
import orre.ai.tasks.Task;
import orre.ai.tasks.TaskRequest;
import orre.ai.tasks.idleTask.IdleTask;
import orre.gameWorld.core.GameObject;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.gameWorld.core.Property;
import orre.gameWorld.core.PropertyDataType;
import orre.gameWorld.messages.NewAssignmentMessage;
import orre.geom.Point2D;
import orre.geom.Point3D;
import orre.geom.mesh.Model;

public abstract class TaskExecutor extends Property {
	private enum TaskExecutorState {
		IDLE,
		REQUESTED_TASK,
		EXECUTING_TASK
	};
	private TaskExecutorState state = TaskExecutorState.IDLE;
	
	private Assignment currentAssignment;

	protected final Enum<?>[] assignableTaskTypes;

	public TaskExecutor(GameObject gameObject, Enum<?> propertyType, Enum<?>[] assignableTaskTypes) {
		super(propertyType, gameObject, true);
		this.assignableTaskTypes = assignableTaskTypes;
	}

	@Override
	public void handleMessage(Message<?> message) {
		if(message.type == MessageType.ASSIGN_TASK) {
			this.abort();
			NewAssignmentMessage newTask = (NewAssignmentMessage) message;
			this.currentAssignment = newTask.getPayload();
			state = TaskExecutorState.EXECUTING_TASK;
		} else if(message.type == MessageType.RUN_ACTION) {
			this.abort();
		}
	}

	private void abort() {
		if(state == TaskExecutorState.EXECUTING_TASK) {
			this.gameObject.world.services.aiService.returnTask(this.gameObject.id);
		}
	}
	
	private void reportAssignmentCompletion() {
		if(state == TaskExecutorState.EXECUTING_TASK) {
			this.gameObject.world.services.aiService.reportAssignmentCompletion(this.gameObject.id);
		}
	}

	@Override
	public void tick() {
		if((state == TaskExecutorState.IDLE) || ((state == TaskExecutorState.EXECUTING_TASK) && currentAssignment.plan.isFinished())) {
			requestNewTask();
		} else if(state == TaskExecutorState.EXECUTING_TASK) {
			currentAssignment.plan.update();
			if(currentAssignment.plan.isFinished()) {
				reportAssignmentCompletion();
				requestNewTask();
			}
		}
	}

	private void requestNewTask() {
		TaskRequest request = generateTaskRequest();
		this.gameObject.world.services.aiService.assignTask(request);
		state = TaskExecutorState.REQUESTED_TASK;
	}

	protected abstract TaskRequest generateTaskRequest();

	@Override
	public void destroy() {

	}

	@Override
	public void init() {
		
	}

}
