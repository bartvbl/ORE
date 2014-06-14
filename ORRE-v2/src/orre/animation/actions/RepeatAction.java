package orre.animation.actions;

import orre.animation.AnimationAction;
import orre.animation.AnimationActionType;
import orre.geom.mesh.Mesh3D;

public class RepeatAction extends AnimationAction {

	public final String targetFrame;

	public RepeatAction(String targetFrame) {
		super(AnimationActionType.REPEAT);
		this.targetFrame = targetFrame;
	}

	@Override
	public void update(Mesh3D target, double percentElapsed, double timeSinceLastUpdate) {}

}
