package orre.ai.tasks;

public class Assignment {
	public final Task[] tasks;
	public final Plan plan;
	public final boolean isExecutionPossible;
	
	public Assignment(Task[] task, Plan plan) {
		this.tasks = task;
		this.plan = plan;
		this.isExecutionPossible = plan.isExecutionPossible();
	}

	public Assignment next(Assignment nextAssignment) {
		Task[] combinedTasks = new Task[nextAssignment.tasks.length + this.tasks.length];
		System.arraycopy(tasks, 0, combinedTasks, 0, tasks.length);
		System.arraycopy(nextAssignment.tasks, 0, combinedTasks, tasks.length, nextAssignment.tasks.length);
		Plan combinedPlan = this.plan.append(nextAssignment.plan);
		
		return new Assignment(combinedTasks, combinedPlan);
	}

	public void abort() {
		System.out.println("Assignment: abort() isn't implemented.");
	}
}
