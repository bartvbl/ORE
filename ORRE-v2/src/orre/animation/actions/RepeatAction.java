package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;

public class RepeatAction extends AnimationAction {

	private final String targetFrame;

	public RepeatAction(String targetFrame) {
		super(AnimationActionType.REPEAT);
		this.targetFrame = targetFrame;
	}

}
