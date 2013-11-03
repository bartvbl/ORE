package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import orre.ai.pathFinding.AStar;
import orre.ai.pathFinding.map.MapTileNode;
import orre.gameWorld.core.GameWorld;

public class TaskMaster {
	private final HashMap<TaskType, ArrayList<PendingTask>> taskStorage = new HashMap<TaskType, ArrayList<PendingTask>>();
	private final TaskType[] priorities = new TaskType[TaskType.values().length];
	private final AStar astar = new AStar();
	private final GameWorld world;
	
	public TaskMaster(GameWorld world) {
		for(TaskType type : TaskType.values()) {
			taskStorage.put(type, new ArrayList<PendingTask>());
		}
		int priorityCounter = 0;
		for(TaskType taskType : TaskType.values()) {
			priorities[priorityCounter] = taskType;
			priorityCounter++;
		}
		this.world = world;
	}
	
	//this method should also require an inventory of some kind.
	public Task assignTask(TaskType[] acceptableTaskTypes) {
		return null;
	}
	
	public void registerPendingTask(PendingTask task) {
		this.taskStorage.get(task.type).add(task);
		MapTileNode startingNode = new MapTileNode(world.map, 2, 2);
		MapTileNode endNode = new MapTileNode(world.map, 30, 30);
		this.astar.findPath(startingNode, endNode);
	}
	
	public void returnUnfinishedTask() {
		
	}
}
