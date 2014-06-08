package orre.ai.tasks;

public class TaskPriorities {
	private final TaskType[] priorities;
	
	public TaskPriorities() {
		this.priorities = TaskType.values();
	}
	
	public void incrementPriority(int priorityIndex) {
		if(priorityIndex <= 0) {
			return;
		}
		if(priorityIndex >= priorities.length) {
			return;
		}
		TaskType temp = priorities[priorityIndex - 1];
		priorities[priorityIndex - 1] = priorities[priorityIndex];
		priorities[priorityIndex] = temp;
	}
	
	public TaskType[] getCurrentPriorities() {
		return priorities.clone();
	}
}
