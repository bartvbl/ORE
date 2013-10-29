package orre.animation;

import java.util.ArrayList;

import orre.geom.mesh.Mesh3D;

public class KeyFrame {

	public final String name;
	public final double duration;
	public final boolean isInfinite;
	private final ArrayList<AnimationAction> frameActions;
	
	public KeyFrame(String name, double duration, boolean isInfinite) {
		this.name = name;
		this.duration = duration;
		this.isInfinite = isInfinite;
		this.frameActions = new ArrayList<AnimationAction>();
	}

	public void addAction(AnimationAction action) {
		this.frameActions.add(action);
	}
	
	public void update(Mesh3D target, double elapsedTime) {
		
	}

}
