package orre.ai.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import orre.ai.tasks.idleTask.IdleTask;
import orre.gameWorld.core.GameWorld;

public class TaskMaster {
	private final GameWorld world;
	private final TaskPriorities priorities;
	private final TaskTracker tracker;
	
	public TaskMaster(GameWorld world, TaskTracker taskTracker) {
		this.world = world;
		this.priorities = new TaskPriorities();
		this.tracker = taskTracker;
	}
	
	public Assignment findAssignment(TaskRequest request) {
		Enum<?>[] priorityList = priorities.getCurrentPriorities();
		for(Enum<?> priority : priorityList) {
			if(canHandleTaskType(priority, request.acceptableTaskTypes) && hasTasksAvailable(priority)) {
				final ArrayList<Task> availableTasks = tracker.getTasksByType(priority);
				Assignment bestAssignment = findBestAssignment(availableTasks, request);
				if(bestAssignment != null) {
					return bestAssignment;
				}
			}
		}
		//no task available -> idle
		IdleTask idleTask = new IdleTask(request.targetID);
		return idleTask.plan(request, this, world);
	}
	
	public void assignTask() {
		
	}

	private boolean canHandleTaskType(Enum<?> priority, Enum<?>[] acceptableTaskTypes) {
		for(Enum<?> acceptableType : acceptableTaskTypes) {
			if(acceptableType == priority) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasTasksAvailable(Enum<?> priority) {
		return tracker.hasTasksAvailable(priority);
	}

	private Assignment findBestAssignment(ArrayList<Task> availableTasks, TaskRequest request) {
		double lowestPlanCost = Double.MAX_VALUE;
		Assignment bestAssignment = null;
		for(Task availableTask : availableTasks) {
			Assignment assignment = availableTask.plan(request, this, world);
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

	public void updatePriorities(Enum<?>[] taskTypePriorities) {
		this.priorities.update(taskTypePriorities);
	}
}