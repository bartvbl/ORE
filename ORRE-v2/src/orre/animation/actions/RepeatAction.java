package orre.animation.actions;

import orre.animation.Animatable;
import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;

public class RepeatAction extends AnimationAction {

	public final String targetFrame;

	public RepeatAction(String targetFrame) {
		super(AnimationActionType.REPEAT);
		this.targetFrame = targetFrame;
	}

	@Override
	public void update(Animatable target, double percentElapsed, double timeSinceLastUpdate) {}

}
