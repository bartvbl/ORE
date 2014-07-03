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
		return new Assignment(idleTask, idleTask.plan(request));
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
		Plan planOfBestTask = null;
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
				planOfBestTask = plan;
				bestTask = availableTask;
			}
		}
		return new Assignment(bestTask, planOfBestTask);
	}

	public void registerPendingTask(Task task) {
		this.taskStorage.get(task.type).add(task);
	}
}