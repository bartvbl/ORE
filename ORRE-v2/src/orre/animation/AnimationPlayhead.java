package orre.animation;

import org.lwjgl.util.Timer;

public class AnimationPlayhead {
	private final Animation animation;
	private final long startingFrameNumber;
	private final Timer timer;

	public AnimationPlayhead(Animation animation, long startingFrameNumber) {
		this.animation = animation;
		this.startingFrameNumber = startingFrameNumber;getClass();
		this.timer = new Timer();
	}
	
	public float getElapsedTime() {
		Timer.tick();
		return timer.getTime();
	}
	
	public long getElapsedFrames(long currentFrameNumber) {
		return currentFrameNumber - startingFrameNumber;
	}
	
	public void reset() {
		this.timer.reset();
	}

}
