package orre.animation.actions;

import java.util.Arrays;

import orre.animation.Animatable;
import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.animation.Ease;
import orre.geom.Axis;
import orre.sceneGraph.CoordinateNode;

public class RotationAction extends AnimationAction {

	private final String partName;
	private final double rotation;
	private final Axis axis;
	private Ease ease;
	private double totalPercentElapsed = 0;

	public RotationAction(String partName, Axis axis, double rotation) {
		super(AnimationActionType.ROTATE);
		this.partName = partName;
		this.rotation = rotation;
		this.axis = axis;
		this.ease = Ease.NO_EASE;
	}
	
	public RotationAction(String partName, Axis axis, double rotation, Ease ease) {
		this(partName, axis, rotation);
		this.ease = ease;
	}

	@Override
	public void update(Animatable target, double percentElapsed, double timeSinceLastUpdate) {
		CoordinateNode part = target.getModelPartByName(partName);
		if(part == null) {
			System.out.println(Arrays.toString(target.getModelParts()));
			throw new RuntimeException("Part " + partName + " was not found in " + target);
		}
		
		double previousPercentage = ease.calculateAnimationPercentage(totalPercentElapsed );
		double currentPercentage = ease.calculateAnimationPercentage(totalPercentElapsed + percentElapsed);
		double percentageDelta = currentPercentage - previousPercentage;
		
		double updatedRotation = rotation * percentageDelta;
		if(axis == Axis.x) {
			part.rotate(updatedRotation, 0, 0);
		} else if(axis == Axis.y) {
			part.rotate(0, updatedRotation, 0);
		} else if(axis == Axis.z) {
			part.rotate(0, 0, updatedRotation);
		}
		totalPercentElapsed += percentElapsed;
	}
	
	public String toString() {
		return "Rotation Action on part " + partName + " around the " + axis + "-axis by " + rotation + " units";
	}

}
