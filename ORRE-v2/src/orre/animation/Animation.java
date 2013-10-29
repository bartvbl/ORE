package orre.animation;

public class Animation {

	public final KeyFrame[] keyFrames;
	public final AnimationType type;

	public Animation(AnimationType type, KeyFrame[] keyFrames) {
		this.keyFrames = keyFrames;
		this.type = type;
	}
	
}
