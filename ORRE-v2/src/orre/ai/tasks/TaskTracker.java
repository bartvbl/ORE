package orre.ai.tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskTracker {
	private final HashMap<Integer, Task[]> assignedMap = new HashMap<Integer, Task[]>();
	private final HashMap<Task, ArrayList<Integer>> assignmentMap = new HashMap<Task, ArrayList<Integer>>();

	public void assign(Assignment assignment, int targetID) {
		for(Task task : assignment.tasks) {
			if(!assignmentMap.containsKey(task)) {
				assignmentMap.put(task, new ArrayList<Integer>());
			}
			assignmentMap.get(task).add(targetID);
			assignedMap.put(targetID, assignment.tasks);
		}
	}
	
	public void abort(int targetID) {
		removeFromAssignentMap(targetID);
	}
	
	public void markCompleted(int targetID) {
		removeFromAssignentMap(targetID);
	}

	private void removeFromAssignentMap(int targetID) {
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
	}

}
