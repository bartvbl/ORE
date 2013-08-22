package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.animation.TransitionType;
import orre.geom.Axis;
import orre.geom.mesh.Mesh3D;

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
	public void update(Mesh3D target, double percentElapsed,
			double timeSinceLastUpdate) {
		// TODO Auto-generated method stub
		
	}

}
