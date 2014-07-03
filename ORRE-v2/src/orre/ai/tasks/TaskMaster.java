package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import orre.ai.pathFinding.AStar;
import orre.gameWorld.core.GameWorld;
import orre.geom.Point2D;

public class TaskMaster {
	private final HashMap<Enum<?>, ArrayList<Task>> taskStorage = new HashMap<Enum<?>, ArrayList<Task>>();
	private final AStar astar = new AStar();
	private final GameWorld world;
	private final TaskPriorities priorities;
	
	public TaskMaster(GameWorld world, TaskPriorities priorities) {
		this.world = world;
		this.priorities = priorities;
	}
	
	public Task assignTask(TaskRequest request) {
		Enum<?>[] priorityList = priorities.getCurrentPriorities();
		for(Enum<?> priority : priorityList) {
			if(canHandleTaskType(priority, request.acceptableTaskTypes)) {
				final ArrayList<Task> availableTasks = taskStorage.get(priority);
				Task foundTask = findTask(availableTasks, request);
				if(foundTask != null) {
					return foundTask;
				}
			}
		}
		//no task available -> idle
		return new IdleTask(request.targetID);
	}
	
	private boolean canHandleTaskType(Enum<?> priority, Enum<?>[] acceptableTaskTypes) {
		for(Enum<?> acceptableType : acceptableTaskTypes) {
			if(acceptableType == priority) {
				return true;
			}
		}
		return false;
	}

	private Task findTask(ArrayList<Task> availableTasks, TaskRequest request) {
		Task bestTask = null;
		double lowestPlanCost = Double.MAX_VALUE;
		for(Task availableTask : availableTasks) {
			Plan plan = availableTask.plan(request);
			if((plan == null) || (!plan.isExecutionPossible())) {
				continue;
			}
			double planCost = plan.getPlanCost();
			if(planCost < lowestPlanCost) {
				lowestPlanCost = planCost;
				bestTask = availableTask;
			}
		}
		return bestTask;
	}


	public void registerPendingTask(Task task) {
		this.taskStorage.get(task.type).add(task);
	}
}