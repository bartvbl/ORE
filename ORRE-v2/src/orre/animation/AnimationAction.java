package orre.animation;

import orre.geom.mesh.Mesh3D;

public abstract class AnimationAction {
	public final AnimationActionType type;
	
	public AnimationAction(AnimationActionType type) {
		this.type = type;
	}
	
	public abstract void update(Mesh3D target, double percentElapsed, double timeSinceLastUpdate);
}
