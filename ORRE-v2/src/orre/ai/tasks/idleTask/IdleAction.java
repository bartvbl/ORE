package orre.ai.tasks.idleTask;

import orre.ai.tasks.Action;

public class IdleAction extends Action {

	@Override
	public boolean isExecutionPossible() {
		return false;
	}

	@Override
	public void update() {

	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public double getCost() {
		return 0;
	}

}
