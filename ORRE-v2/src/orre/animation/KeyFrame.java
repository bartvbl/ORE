package orre.animation;

public class KeyFrame {

	public final String name;
	public final double duration;
	public final boolean isInfinite;
	public final boolean isInstant;
	public final AnimationAction[] actions;
	
	public KeyFrame(String name, double duration, boolean isInfinite, boolean isInstant, AnimationAction[] frameActions) {
		this.name = name;
		this.duration = duration;
		this.isInfinite = isInfinite;
		this.isInstant = isInstant;
		this.actions = frameActions;
	}
}
