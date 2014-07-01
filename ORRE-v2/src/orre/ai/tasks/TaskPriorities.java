package orre.ai.tasks;

import java.util.ArrayList;

public class TaskPriorities {
	private final ArrayList<Enum<?>> priorities;
	
	public TaskPriorities() {
		this.priorities = new ArrayList<Enum<?>>();
	}
	
	public void incrementPriority(int priorityIndex) {
		if(priorityIndex <= 0) {
			return;
		}
		if(priorityIndex >= priorities.size()) {
			return;
		}
		Enum<?> temp = priorities.get(priorityIndex - 1);
		priorities.set(priorityIndex - 1, priorities.get(priorityIndex));
		priorities.set(priorityIndex, temp);
	}
	
	public Enum<?>[] getCurrentPriorities() {
		return priorities.toArray(new Enum<?>[priorities.size()]);
	}
}
