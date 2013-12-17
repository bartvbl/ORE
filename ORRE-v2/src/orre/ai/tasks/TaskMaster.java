package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

import orre.ai.pathFinding.AStar;
import orre.ai.pathFinding.Path;
import orre.ai.pathFinding.map.AnimationGenerator;
import orre.ai.pathFinding.map.MapTileNode;
import orre.animation.Animation;
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
	
	public Task assignTask(int gameObjectID, TaskType[] acceptableTaskTypes) {
		MapTileNode startingNode = new MapTileNode(world.map, 2, 2);
		MapTileNode endNode = new MapTileNode(world.map, 48, 52);
		Path path = this.astar.findPath(startingNode, endNode);
		Animation animation = AnimationGenerator.generateAnimation(path);
		return new Task(TaskType.COLLECT_ORE, animation, gameObjectID);
	}
	
	public void registerPendingTask(PendingTask task) {
		this.taskStorage.get(task.type).add(task);
	}
}
