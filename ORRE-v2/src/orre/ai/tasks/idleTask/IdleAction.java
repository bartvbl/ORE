package orre.ai.tasks.idleTask;

import org.lwjgl.util.Timer;

import orre.ai.tasks.Action;

public class IdleAction extends Action {
	private boolean hasStarted = false;
	private final Timer timer = new Timer();
	private boolean isFinished = false;
	
	private static final float RETRY_TIME = 10;
	

	@Override
	public boolean isExecutionPossible() {
		return true;
	}

	@Override
	public void update() {
		Timer.tick();
		if(!hasStarted) {
			hasStarted = true;
			timer.reset();
			timer.resume();
		}
		if(timer.getTime() > RETRY_TIME) {
			isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public double getCost() {
		return 0;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void end() {
		
	}

}
