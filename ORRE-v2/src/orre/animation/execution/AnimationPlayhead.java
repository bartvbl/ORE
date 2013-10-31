package orre.animation.execution;

import org.lwjgl.util.Timer;

import orre.animation.Animation;
import orre.animation.AnimationAction;
import orre.animation.KeyFrame;
import orre.geom.mesh.Mesh3D;

public class AnimationPlayhead {
	private final Animation animation;
	private final Timer timer;
	private final Mesh3D target;
	private float previousFrameStartTime = 0;
	private float keyFrameStartTime = 0;
	private int currentFrameID = 0;

	public AnimationPlayhead(Animation animation, Mesh3D target) {
		this.animation = animation;
		this.timer = new Timer();
		this.target = target;
	}
	
	public void play() {
		Timer.tick();
		this.keyFrameStartTime = timer.getTime();
	}
	
	public void updateAnimation() {
		KeyFrame currentFrame = animation.keyFrames[currentFrameID];
		float currentTime = timer.getTime();
		double elapsedTimeInFrame = currentTime - keyFrameStartTime;
		double elapsedTime = currentTime - previousFrameStartTime;
		
		if(elapsedTimeInFrame > currentFrame.duration) {
			if(currentFrame.isInfinite) {
				updateFrame(elapsedTime);
			} else {
				updateFrame(currentFrame.duration - previousFrameStartTime);
				nextFrame();
				updateFrame(elapsedTimeInFrame - currentFrame.duration);
			}
		} else {
			updateFrame(elapsedTime);
		}
		previousFrameStartTime = currentTime;
	}

	public void updateFrame(double elapsedTime) {
		KeyFrame currentFrame = animation.keyFrames[currentFrameID];
		double percentElapsed = elapsedTime / currentFrame.duration;
		
		for(AnimationAction action : currentFrame.actions) {
			action.update(target, percentElapsed, elapsedTime);
		}
	}
	
	private void nextFrame() {
		this.currentFrameID++;
	}
	
	public void reset() {
		this.timer.reset();
	}

	public boolean isFinished() {
		return animation.keyFrames.length == currentFrameID;
	}

}
