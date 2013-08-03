package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;

public class MoveAction extends AnimationAction {

	private final String partName;
	private final Axis axis;
	private final double speedUnits;

	public MoveAction(String partName, Axis axis, double speedUnits) {
		super(AnimationActionType.MOVE);
		this.partName = partName;
		this.axis = axis;
		this.speedUnits = speedUnits;
	}

}
