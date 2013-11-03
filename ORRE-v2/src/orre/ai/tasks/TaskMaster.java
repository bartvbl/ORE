package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskMaster {
	private final HashMap<TaskType, ArrayList<PendingTask>> taskStorage = new HashMap<TaskType, ArrayList<PendingTask>>();
	private final TaskType[] priorities = new TaskType[TaskType.values().length];
	
	public TaskMaster() {
		for(TaskType type : TaskType.values()) {
			taskStorage.put(type, new ArrayList<PendingTask>());
		}
		int priorityCounter = 0;
		for(TaskType taskType : TaskType.values()) {
			priorities[priorityCounter] = taskType;
			priorityCounter++;
		}
	}
	
	//this method should also require an inventory of some kind.
	public Task assignTask(TaskType[] acceptableTaskTypes) {
		return null;
	}
	
	public void registerPendingTask(PendingTask task) {
		this.taskStorage.get(task.type).add(task);
	}
	
	public void returnUnfinishedTask() {
		
	}
}
