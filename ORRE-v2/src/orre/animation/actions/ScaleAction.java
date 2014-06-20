package orre.animation.actions;

import orre.animation.Animatable;
import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;

public class ScaleAction extends AnimationAction {

	private final String partName;
	private final Axis axis;
	private final double percentage;

	public ScaleAction(String partName, Axis axis, double percentage) {
		super(AnimationActionType.SCALE);
		this.partName = partName;
		this.axis = axis;
		this.percentage = percentage;
	}

	@Override
	public void update(Animatable target, double percentElapsed, double timeSinceLastUpdate) {
		
	}

}
