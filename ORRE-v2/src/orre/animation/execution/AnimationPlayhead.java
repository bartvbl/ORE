package orre.animation.execution;

import org.lwjgl.util.Timer;

import orre.animation.Animatable;
import orre.animation.Animation;
import orre.animation.AnimationAction;
import orre.animation.KeyFrame;
import orre.animation.actions.RepeatAction;
import orre.geom.mesh.Mesh3D;

public class AnimationPlayhead {
	private final Animation animation;
	private final Timer timer;
	private final Animatable target;
	private float previousFrameStartTime = 0;
	private int currentFrameID = 0;
	private double elapsedTimeInFrame = 0;
	private boolean isFinished = false;

	public AnimationPlayhead(Animation animation, Animatable target) {
		this.animation = animation;
		this.timer = new Timer();
		this.target = target;
	}
	
	public void updateAnimation() {
		KeyFrame currentFrame = animation.keyFrames[currentFrameID];
		float currentTime = timer.getTime();
		double elapsedTime = currentTime - previousFrameStartTime;
		
		if(elapsedTimeInFrame + elapsedTime > currentFrame.duration) {
			if(currentFrame.isInfinite) {
				updateFrame(elapsedTime);
			} else {
				updateFrame(currentFrame.duration - elapsedTimeInFrame);
				double remainingTime = elapsedTimeInFrame + elapsedTime - currentFrame.duration;
				elapsedTimeInFrame -= currentFrame.duration;
				nextFrame();
				if(!isFinished()) {
					updateFrame(remainingTime);
				}
			}
		} else {
			updateFrame(elapsedTime);
		}
		
		elapsedTimeInFrame += elapsedTime;
		previousFrameStartTime = currentTime;
	}

	private void updateFrame(double elapsedTime) {
		KeyFrame currentFrame = animation.keyFrames[currentFrameID];
		double percentElapsed = elapsedTime / currentFrame.duration;
		
		for(AnimationAction action : currentFrame.actions) {
			action.update(target, percentElapsed, elapsedTime);
		}
	}
	
	private void gotoFrame(String targetFrame) {
		for(int i = 0; i < animation.keyFrames.length; i++) {
			if(animation.keyFrames[i].name.equals(targetFrame)) {
				this.currentFrameID = i;
			}
		}
	}

	private void nextFrame() {
		KeyFrame currentFrame = animation.keyFrames[currentFrameID];
		boolean jumped = false;
		for(AnimationAction action : currentFrame.actions) {
			if(action instanceof RepeatAction) {
				gotoFrame(((RepeatAction) action).targetFrame);
				jumped = true;
			}
		}
		if(!jumped) {
			this.currentFrameID++;
			if(this.currentFrameID >= animation.keyFrames.length) {
				this.isFinished  = true;
			}
		}
	}
	
	public void reset() {
		this.timer.reset();
	}

	public boolean isFinished() {
		return this.isFinished;
	}

}
