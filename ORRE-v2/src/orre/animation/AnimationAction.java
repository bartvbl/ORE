package orre.animation;

public abstract class AnimationAction {
	public final AnimationActionType type;
	
	public AnimationAction(AnimationActionType type) {
		this.type = type;
	}
	
	public abstract void update(Animatable target, double percentElapsed, double timeSinceLastUpdate);
}
