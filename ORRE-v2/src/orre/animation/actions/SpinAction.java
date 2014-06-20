package orre.animation.actions;

import orre.animation.Animatable;
import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.sceneGraph.CoordinateNode;

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

	@Override
	public void update(Animatable target, double percentElapsed, double timeSinceLastUpdate) {
		CoordinateNode part = target.getModelPartByName(partName);
		double updatedRotation = timeSinceLastUpdate * speedDegrees;
		if(axis == Axis.x) {
			part.rotate(updatedRotation, 0, 0);
		} else if(axis == Axis.y) {
			part.rotate(0, updatedRotation, 0);
		} else if(axis == Axis.z) {
			part.rotate(0, 0, updatedRotation);
		}
	}

}
