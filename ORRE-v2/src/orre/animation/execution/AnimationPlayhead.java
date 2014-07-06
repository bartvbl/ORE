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
		
		if(currentFrame.isInstant) {
			updateFrame(0, true);
			nextFrame();
			if(this.isFinished()) {
				return;
			}
		}
		
		if(elapsedTimeInFrame + elapsedTime > currentFrame.duration) {
			if(currentFrame.isInfinite) {
				updateFrame(elapsedTime, false);
			} else {
				updateFrame(currentFrame.duration - elapsedTimeInFrame, false);
				double remainingTime = elapsedTimeInFrame + elapsedTime - currentFrame.duration;
				elapsedTimeInFrame -= currentFrame.duration;
				nextFrame();
				if(!isFinished()) {
					updateFrame(remainingTime, false);
				}
			}
		} else {
			updateFrame(elapsedTime, false);
		}
		
		elapsedTimeInFrame += elapsedTime;
		previousFrameStartTime = currentTime;
	}

	private void updateFrame(double elapsedTime, boolean isInstantUpdate) {
		KeyFrame currentFrame = animation.keyFrames[currentFrameID];
		
		double percentElapsed;
		if(isInstantUpdate) {
			percentElapsed = 1;
		} else {
			percentElapsed = elapsedTime / currentFrame.duration;
		}
		
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

	public void notifyAnimationEnd() {
		target.notifyAnimationEnd();
	}

}
