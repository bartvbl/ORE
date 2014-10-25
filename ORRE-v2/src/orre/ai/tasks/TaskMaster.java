package orre.ai.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import orre.ai.tasks.idleTask.IdleTask;
import orre.gameWorld.core.GameWorld;

public class TaskMaster {
	private final HashMap<Enum<?>, ArrayList<Task>> taskStorage = new HashMap<Enum<?>, ArrayList<Task>>();
	private final GameWorld world;
	private final TaskPriorities priorities;
	private final TaskSupplier taskSupplier = new TaskSupplier();
	
	public TaskMaster(GameWorld world) {
		this.world = world;
		this.priorities = new TaskPriorities();
	}
	
	public Assignment assignTask(TaskRequest request) {
		System.out.println("Handling task assignment: " + Arrays.toString(request.acceptableTaskTypes));
		Enum<?>[] priorityList = priorities.getCurrentPriorities();
		for(Enum<?> priority : priorityList) {
			if(canHandleTaskType(priority, request.acceptableTaskTypes)) {
				if(hasTasksAvailable(priority)) {
					final ArrayList<Task> availableTasks = taskStorage.get(priority);
					Assignment bestAssignment = findBestAssignment(availableTasks, request);
					if(bestAssignment != null) {
						return bestAssignment;
					}
				}
			}
		}
		//no task available -> idle
		IdleTask idleTask = new IdleTask(request.targetID);
		return idleTask.plan(request, this);
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
		return taskStorage.containsKey(priority);
	}

	private Assignment findBestAssignment(ArrayList<Task> availableTasks, TaskRequest request) {
		double lowestPlanCost = Double.MAX_VALUE;
		Assignment bestAssignment = null;
		for(Task availableTask : availableTasks) {
			Assignment assignment = availableTask.plan(request, this);
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
		System.out.println("Registering new task of type " + task.type);
		if(!this.taskStorage.containsKey(task.type)) {
			this.taskStorage.put(task.type, new ArrayList<Task>());
		}
		this.taskStorage.get(task.type).add(task);
	}
	
	public void updatePriorities(Enum<?>[] taskTypePriorities) {
		this.priorities.update(taskTypePriorities);
	}

	public void returnTask(Task[] tasks) {
		for(Task task : tasks) {
			this.taskStorage.get(task.type).add(task);
		}
	}
}