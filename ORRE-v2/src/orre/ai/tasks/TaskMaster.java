package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import orre.ai.pathFinding.AStar;
import orre.ai.pathFinding.Path;
import orre.ai.pathFinding.map.AnimationGenerator;
import orre.ai.pathFinding.map.MapTileNode;
import orre.animation.Animation;
import orre.gameWorld.core.GameWorld;
import orre.geom.Point2D;

public class TaskMaster {
	private final HashMap<TaskType, ArrayList<PendingTask>> taskStorage = new HashMap<TaskType, ArrayList<PendingTask>>();
	private final AStar astar = new AStar();
	private final GameWorld world;
	private final TaskPriorities priorities;
	
	public TaskMaster(GameWorld world, TaskPriorities priorities) {
		for(TaskType type : TaskType.values()) {
			taskStorage.put(type, new ArrayList<PendingTask>());
		}
		this.world = world;
		this.priorities = priorities;
	}
	
	public Task assignTask(int gameObjectID, TaskType[] acceptableTaskTypes, Point2D locationOnMap) {
		TaskType[] priorityList = priorities.getCurrentPriorities();
		for(TaskType priority : priorityList) {
			if(canHandleTaskType(priority, acceptableTaskTypes)) {
				ArrayList<PendingTask> availableTasks = taskStorage.get(priority);
				PendingTask foundTask = findTask(availableTasks, locationOnMap);
				if(foundTask != null) {
					return foundTask.task;
				}
			}
		}
		//no task available -> idle
		return new IdleTask(gameObjectID);
	}
	
	private boolean canHandleTaskType(TaskType priority, TaskType[] acceptableTaskTypes) {
		return false;
	}

	private PendingTask findTask(ArrayList<PendingTask> availableTasks, Point2D locationOnMap) {
		return null;
	}

	public void registerPendingTask(PendingTask task) {
		this.taskStorage.get(task.taskType).add(task);
	}
}
