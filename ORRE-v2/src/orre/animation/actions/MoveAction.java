package orre.animation.actions;

import orre.animation.Animatable;
import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.sceneGraph.CoordinateNode;

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
	
	@Override
	public void update(Animatable target, double percentElapsed, double timeSinceLastUpdate) {
		CoordinateNode part = target.getModelPartByName(partName);
		double distanceTranslated = speedUnits * timeSinceLastUpdate;
		if(axis == Axis.x) {
			part.translate(distanceTranslated, 0, 0);
		} else if(axis == Axis.y) {
			part.translate(0, distanceTranslated, 0);
		} else if(axis == Axis.z) {
			part.translate(0, 0, distanceTranslated);
		}
	}
	
	public String toString() {
		return "MoveAction along the " + axis + "-axis at " + speedUnits + " units";
	}

}
