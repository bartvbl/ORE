package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;

public class SpinAction extends AnimationAction {

	private final String partName;
	private final Axis axis;
	private final double speedDegrees;

	public SpinAction(String partName, Axis axis, double speedDegrees) {
		super(AnimationActionType.SPIN);
		this.partName = partName;
		this.axis = axis;
		this.speedDegrees = speedDegrees;
	}

}
