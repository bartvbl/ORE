package orre.animation.execution;

import org.lwjgl.util.Timer;

import orre.animation.Animation;
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
		float currentTime = timer.getTime();
		double elapsedTime = currentTime - previousFrameStartTime;
		KeyFrame currentFrame = animation.keyFrames[currentFrameID];
		if(!currentFrame.isInfinite) {
			double percentageUpdate = elapsedTime / currentFrame.duration;
			
		} else {
			
		}
		//animation.keyFrames[currentKeyFrame].update(target, elapsedTime);
		previousFrameStartTime = currentTime;
		
	}
	
	public void reset() {
		this.timer.reset();
	}

	public boolean isFinished() {
		return false;
	}

}
