package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskTracker {
	private final HashMap<Integer, Task[]> assignedMap = new HashMap<Integer, Task[]>();
	private final HashMap<Task, ArrayList<Integer>> assignmentMap = new HashMap<Task, ArrayList<Integer>>();
	private final HashMap<Enum<?>, ArrayList<Task>> taskStorage = new HashMap<Enum<?>, ArrayList<Task>>();

	public void assign(Assignment assignment, int targetID) {
		for(Task task : assignment.tasks) {
			if(!assignmentMap.containsKey(task)) {
				assignmentMap.put(task, new ArrayList<Integer>());
			}
			assignmentMap.get(task).add(targetID);
			assignedMap.put(targetID, assignment.tasks);
			if(taskStorage.get(task.type) != null) {
				taskStorage.get(task.type).remove(task);
			}
		}
	}
	
	public void abort(int targetID) {
		Task[] removedTasks = removeFromAssignentMap(targetID);
		for(Task task : removedTasks) {
			this.taskStorage.get(task.type).add(task);
		}
	}
	
	public void markCompleted(int targetID) {
		removeFromAssignentMap(targetID); //discard tasks returned: they're completed now.
	}

	private Task[] removeFromAssignentMap(int targetID) {
		assert assignedMap.containsKey(targetID);
		Task[] assignedTasks = assignedMap.remove(targetID);
		for(Task task : assignedTasks) {
			ArrayList<Integer> currentAssignments = assignmentMap.get(task);
			if(currentAssignments.size() == 1) {
				assignmentMap.remove(task);
			} else {
				currentAssignments.remove(targetID);
			}
		}
		return assignedTasks;
	}

	public ArrayList<Task> getTasksByType(Enum<?> type) {
		return taskStorage.get(type);
	}

	public boolean hasTasksAvailable(Enum<?> type) {
		ArrayList<Task> availableTasks = taskStorage.get(type);
		return ((availableTasks != null) && (!taskStorage.get(type).isEmpty()));
	}
	
	public void registerPendingTask(Task task) {
		if(!this.taskStorage.containsKey(task.type)) {
			this.taskStorage.put(task.type, new ArrayList<Task>());
		}
		this.taskStorage.get(task.type).add(task);
	}

}
