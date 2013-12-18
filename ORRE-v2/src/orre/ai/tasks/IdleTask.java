package orre.ai.tasks;

import orre.animation.Animation;

public class IdleTask extends Task {

	public IdleTask(int gameObjectID) {
		super(TaskType.IDLE, gameObjectID);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
