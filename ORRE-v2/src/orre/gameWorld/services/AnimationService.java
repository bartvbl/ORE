package orre.gameWorld.services;

import java.util.ArrayList;

import org.lwjgl.util.Timer;

import orre.animation.Animation;
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
			playHead.updateAnimation();
		}
		for(int i = 0; i < activeAnimations.size(); i++) {
			if(activeAnimations.get(i).isFinished()) {
				activeAnimations.remove(i);
				i--;
			}
		}
	}

	public void applyAnimation(AnimationType type, Mesh3D animatable) {
		applyAnimation(world.resourceCache.getAnimation(type), animatable);
	}

	public void applyAnimation(Animation animation, Mesh3D target) {
		AnimationPlayhead playHead = new AnimationPlayhead(animation, target);
		activeAnimations.add(playHead);
	}

}
