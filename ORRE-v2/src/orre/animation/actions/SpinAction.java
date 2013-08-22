package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.geom.mesh.Mesh3D;

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
	public void update(Mesh3D target, double percentElapsed,
			double timeSinceLastUpdate) {
		// TODO Auto-generated method stub
		
	}

}
