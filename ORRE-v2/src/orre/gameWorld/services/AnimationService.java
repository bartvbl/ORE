package orre.gameWorld.services;

import java.util.ArrayList;

import org.lwjgl.util.Timer;

import orre.animation.AnimationType;
import orre.animation.execution.AnimationPlayhead;
import orre.gameWorld.core.GameWorld;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceCache;
import orre.sceneGraph.SceneNode;

public class AnimationService implements Service {
	private final GameWorld world;
	
	private ArrayList<AnimationPlayhead> activeAnimations = new ArrayList<AnimationPlayhead>();


	public AnimationService(GameWorld world) {
		this.world = world;
	}
	
	public void tick() {
		Timer.tick();
		for(AnimationPlayhead playHead : activeAnimations) {
			if(playHead.isFinished()) {
				activeAnimations.remove(playHead);
			} else {
				playHead.updateAnimation();
			}
		}
	}

	public void applyAnimation(AnimationType type, Mesh3D animatable) {
		AnimationPlayhead playHead = new AnimationPlayhead(world.resourceCache.getAnimation(type), animatable);
		activeAnimations.add(playHead);
	}

}
