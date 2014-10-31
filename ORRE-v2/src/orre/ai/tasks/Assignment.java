package orre.ai.tasks;

public class Assignment {
	public final Task[] tasks;
	public final Plan plan;
	
	public Assignment(Task[] task, Plan plan) {
		this.tasks = task;
		this.plan = plan;
	}

	public Assignment next(Assignment nextAssignment) {
		Task[] combinedTasks = new Task[nextAssignment.tasks.length + this.tasks.length];
		Plan combinedPlan = this.plan.append(nextAssignment.plan);
		
		return new Assignment(combinedTasks, combinedPlan);
	}
}
