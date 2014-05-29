package orre.gameWorld.services;

import java.util.ArrayList;

import org.lwjgl.util.Timer;

import orre.animation.Animation;
import orre.animation.AnimationType;
import orre.animation.execution.AnimationPlayhead;
import orre.gameWorld.core.GameWorld;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.sceneGraph.SceneNode;

public class AnimationService implements Service {
	private final GameWorld world;
	
	private ArrayList<AnimationPlayhead> activeAnimations = new ArrayList<AnimationPlayhead>();


	public AnimationService(GameWorld world) {
		this.world = world;
	}
	
	public void tick() {
		Timer.tick();
		for(int i = activeAnimations.size() - 1; i >= 0; i--) {
			AnimationPlayhead playHead = activeAnimations.get(i);
			try {
				playHead.updateAnimation();
			} catch(NullPointerException e) {
				activeAnimations.remove(playHead);
				System.err.println("Animation caused an error. Aborting. Message: " + e.getMessage());
			}
		}
		for(int i = 0; i < activeAnimations.size(); i++) {
			if(activeAnimations.get(i).isFinished()) {
				activeAnimations.remove(i);
				i--;
			}
		}
	}

	public void applyAnimation(AnimationType type, Mesh3D animatable) {
		Animation animation = (Animation) world.resourceCache.getResource(ResourceType.ANIMATION_FILE, type.toString()).content;
		applyAnimation(animation, animatable);
	}

	public void applyAnimation(Animation animation, Mesh3D target) {
		AnimationPlayhead playHead = new AnimationPlayhead(animation, target);
		activeAnimations.add(playHead);
	}

}
