package orre.animation;

public abstract class AnimationAction {
	public final AnimationActionType type;
	
	public AnimationAction(AnimationActionType type) {
		this.type = type;
	}
}
