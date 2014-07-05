package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import orre.ai.tasks.idleTask.IdleTask;
import orre.gameWorld.core.GameWorld;

public class TaskMaster {
	private final HashMap<Enum<?>, ArrayList<Task>> taskStorage = new HashMap<Enum<?>, ArrayList<Task>>();
	private final GameWorld world;
	private final TaskPriorities priorities;
	private final TaskSupplier taskSupplier = new TaskSupplier();
	
	public TaskMaster(GameWorld world, TaskPriorities priorities) {
		this.world = world;
		this.priorities = priorities;
	}
	
	public Assignment assignTask(TaskRequest request) {
		Enum<?>[] priorityList = priorities.getCurrentPriorities();
		for(Enum<?> priority : priorityList) {
			if(canHandleTaskType(priority, request.acceptableTaskTypes)) {
				final ArrayList<Task> availableTasks = taskStorage.get(priority);
				Assignment bestAssignment = findBestAssignment(availableTasks, request);
				if(bestAssignment != null) {
					return bestAssignment;
				}
			}
		}
		//no task available -> idle
		IdleTask idleTask = new IdleTask(request.targetID);
		return idleTask.plan(request);
	}
	
	private boolean canHandleTaskType(Enum<?> priority, Enum<?>[] acceptableTaskTypes) {
		for(Enum<?> acceptableType : acceptableTaskTypes) {
			if(acceptableType == priority) {
				return true;
			}
		}
		return false;
	}

	private Assignment findBestAssignment(ArrayList<Task> availableTasks, TaskRequest request) {
		double lowestPlanCost = Double.MAX_VALUE;
		Assignment bestAssignment = null;
		for(Task availableTask : availableTasks) {
			Assignment assignment = availableTask.plan(request);
			if((assignment.plan == null) || (!assignment.plan.isExecutionPossible())) {
				continue;
			}
			double planCost = assignment.plan.getPlanCost();
			if(planCost < lowestPlanCost) {
				lowestPlanCost = planCost;
				bestAssignment = assignment;
			}
		}
		return bestAssignment;
	}

	public void registerPendingTask(Task task) {
		this.taskStorage.get(task.type).add(task);
	}

	public void returnTask(Task[] tasks) {
		for(Task task : tasks) {
			this.taskStorage.get(task.type).add(task);
		}
	}
}