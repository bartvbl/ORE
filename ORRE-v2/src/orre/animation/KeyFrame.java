package orre.animation;

public class KeyFrame {

	public final String name;
	public final double duration;
	public final boolean isInfinite;
	public final AnimationAction[] actions;
	
	public KeyFrame(String name, double duration, boolean isInfinite, AnimationAction[] frameActions) {
		this.name = name;
		this.duration = duration;
		this.isInfinite = isInfinite;
		this.actions = frameActions;
	}
}
