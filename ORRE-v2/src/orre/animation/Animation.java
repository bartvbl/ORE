package orre.animation;

public class Animation {

	private final KeyFrame[] keyFrames;
	public final AnimationType type;

	public Animation(AnimationType type, KeyFrame[] keyFrames) {
		this.keyFrames = keyFrames;
		this.type = type;
	}
	
}
