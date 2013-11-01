package orre.ai;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskMaster {
	private final HashMap<TaskType, ArrayList<Task>> taskStorage = new HashMap<TaskType, ArrayList<Task>>();
	private final TaskType[] priorities = new TaskType[TaskType.values().length];
	
	public TaskMaster() {
		for(TaskType type : TaskType.values()) {
			taskStorage.put(type, new ArrayList<Task>());
		}
		
	}
}
