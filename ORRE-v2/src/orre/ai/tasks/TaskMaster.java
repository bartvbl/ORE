package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import orre.ai.pathFinding.AStar;
import orre.ai.pathFinding.Path;
import orre.gameWorld.core.GameWorld;
import orre.geom.Point2D;

public class TaskMaster {
	private final HashMap<TaskType, ArrayList<Task>> taskStorage = new HashMap<TaskType, ArrayList<Task>>();
	private final AStar astar = new AStar();
	private final GameWorld world;
	private final TaskPriorities priorities;
	
	public TaskMaster(GameWorld world, TaskPriorities priorities) {
		for(TaskType type : TaskType.values()) {
			taskStorage.put(type, new ArrayList<Task>());
		}
		this.world = world;
		this.priorities = priorities;
	}
	
	public Task assignTask(int gameObjectID, TaskType[] acceptableTaskTypes, Point2D locationOnMap) {
		TaskType[] priorityList = priorities.getCurrentPriorities();
		for(TaskType priority : priorityList) {
			if(canHandleTaskType(priority, acceptableTaskTypes)) {
				final ArrayList<Task> availableTasks = taskStorage.get(priority);
				Task foundTask = findTask(availableTasks, locationOnMap);
				if(foundTask != null) {
					return foundTask;
				}
			}
		}
		//no task available -> idle
		return new IdleTask(gameObjectID);
	}
	
	private boolean canHandleTaskType(TaskType priority, TaskType[] acceptableTaskTypes) {
		for(TaskType acceptableType : acceptableTaskTypes) {
			if(acceptableType == priority) {
				return true;
			}
		}
		return false;
	}

	private Task findTask(ArrayList<Task> availableTasks, Point2D locationOnMap) {
		Task bestTask = null;
		double lowestPlanCost = Double.MAX_VALUE;
		for(Task availableTask : availableTasks) {
			availableTask.plan(locationOnMap);
			if(!availableTask.isExecutionPossible()) {
				continue;
			}
			double planCost = availableTask.getPlanCost();
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