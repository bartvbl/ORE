package orre.ai.tasks;

public class Assignment {
	public final Task[] tasks;
	public final Plan plan;
	
	public Assignment(Task[] task, Plan plan) {
		this.tasks = task;
		this.plan = plan;
	}
}
