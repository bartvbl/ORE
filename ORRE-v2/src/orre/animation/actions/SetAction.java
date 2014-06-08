package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.animation.TransitionType;
import orre.geom.Axis;
import orre.geom.mesh.Mesh3D;
import orre.geom.mesh.ModelPart;

public class SetAction extends AnimationAction {

	private final String partName;
	private final Axis axis;
	private final TransitionType valueType;
	private final double value;

	public SetAction(String partName, Axis axis, TransitionType type, double value) {
		super(AnimationActionType.SET);
		this.partName = partName;
		this.axis = axis;
		this.valueType = type;
		this.value = value;
	}

	@Override
	public void update(Mesh3D target, double percentElapsed, double timeSinceLastUpdate) {
		ModelPart part = target.getModelPartByName(partName);
		if(valueType == TransitionType.position) {
			if(axis == Axis.x) {
				part.setX(value);
			} else if(axis == Axis.y) {
				part.setY(value);
			} else if(axis == Axis.z) {
				part.setZ(value);
			}
		} else if(valueType == TransitionType.rotation) {
			if(axis == Axis.x) {
				part.setRotationX(value);
			} else if(axis == Axis.y) {
				part.setRotationY(value);
			} else if(axis == Axis.z) {
				part.setRotationZ(value);
			}
		}
	}

}
