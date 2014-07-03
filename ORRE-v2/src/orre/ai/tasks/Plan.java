package orre.ai.tasks;

public abstract class Plan {
	public abstract boolean isExecutionPossible();
	public abstract void update();
	public abstract boolean isFinished();
	public abstract double getPlanCost();
}
