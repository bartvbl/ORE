package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.Axis;
import orre.geom.mesh.Mesh3D;

public class TranslateAction extends AnimationAction {

	private final String partName;
	private final Axis axis;
	private final double units;

	public TranslateAction(String partName, Axis axis, double units) {
		super(AnimationActionType.TRANSLATE);
		this.partName = partName;
		this.axis = axis;
		this.units = units;
	}

	@Override
	public void update(Mesh3D target, double percentElapsed,
			double timeSinceLastUpdate) {
		// TODO Auto-generated method stub
		
	}

}
