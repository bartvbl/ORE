package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.geom.mesh.Mesh3D;
import orre.geom.mesh.ModelPart;

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
	
	public void update(Mesh3D target, double percentElapsed, double timeSinceLastUpdate) {
		ModelPart part = target.getModelPartByName(partName);
		double distanceTranslated = speedUnits * timeSinceLastUpdate;
		if(axis == Axis.x) {
			part.translate(distanceTranslated, 0, 0);
		} else if(axis == Axis.y) {
			part.translate(0, distanceTranslated, 0);
		} else if(axis == Axis.z) {
			part.translate(0, 0, distanceTranslated);
		}
	}

}
