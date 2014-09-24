package orre.animation.actions;

import orre.animation.Animatable;
import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.sceneGraph.CoordinateNode;

public class RotationAction extends AnimationAction {

	private final String partName;
	private final double rotation;
	private final Axis axis;

	public RotationAction(String partName, Axis axis, double rotation) {
		super(AnimationActionType.ROTATE);
		this.partName = partName;
		this.rotation = rotation;
		this.axis = axis;
	}

	@Override
	public void update(Animatable target, double percentElapsed, double timeSinceLastUpdate) {
		CoordinateNode part = target.getModelPartByName(partName);
		double updatedRotation = rotation * percentElapsed;
		if(axis == Axis.x) {
			part.rotate(updatedRotation, 0, 0);
		} else if(axis == Axis.y) {
			part.rotate(0, updatedRotation, 0);
		} else if(axis == Axis.z) {
			part.rotate(0, 0, updatedRotation);
		}
	}

}
