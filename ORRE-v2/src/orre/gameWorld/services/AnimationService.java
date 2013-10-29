package orre.gameWorld.services;

import java.util.ArrayList;

import orre.animation.AnimationType;
import orre.animation.execution.AnimationPlayhead;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceCache;
import orre.sceneGraph.SceneNode;

public class AnimationService implements Service {
	private final ResourceCache cache;
	
	private ArrayList<AnimationPlayhead> activeAnimations = new ArrayList<AnimationPlayhead>();

	public AnimationService(ResourceCache cache) {
		this.cache = cache;
	}
	
	public void tick() {
		for(AnimationPlayhead playHead : activeAnimations) {
			if(playHead.isFinished()) {
				activeAnimations.remove(playHead);
			} else {
				playHead.updateAnimation();
			}
		}
	}

	public void applyAnimation(AnimationType type, Mesh3D animatable) {
		activeAnimations.add(new AnimationPlayhead(cache.getAnimation(type), animatable));
	}

}
