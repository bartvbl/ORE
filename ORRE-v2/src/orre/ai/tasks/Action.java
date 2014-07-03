package orre.ai.tasks;

public abstract class Action {

	public abstract boolean isExecutionPossible();
	public abstract void update();
	public abstract boolean isFinished();
	public abstract double getCost();
	
}
