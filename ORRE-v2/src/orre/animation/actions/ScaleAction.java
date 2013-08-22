package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.geom.mesh.Mesh3D;

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
	public void update(Mesh3D target, double percentElapsed,
			double timeSinceLastUpdate) {
		// TODO Auto-generated method stub
		
	}

}
