package orre.animation;

public class Animation {

	public final String name;
	private final KeyFrame[] keyFrames;

	public Animation(String animationName, KeyFrame[] keyFrames) {
		this.keyFrames = keyFrames;
		this.name = animationName;
	}
	
}
