package orre.gameWorld.services;

import java.util.ArrayList;

import org.lwjgl.util.Timer;

import orre.animation.Animatable;
import orre.animation.Animation;
import orre.animation.AnimationType;
import orre.animation.execution.AnimationPlayhead;
import orre.gameWorld.core.GameWorld;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceType;

public class AnimationService implements Service {
	private final GameWorld world;
	
	private ArrayList<AnimationPlayhead> activeAnimations = new ArrayList<AnimationPlayhead>();


	public AnimationService(GameWorld world) {
		this.world = world;
	}
	
	@Override
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
				activeAnimations.get(i).notifyAnimationEnd();
				activeAnimations.remove(i);
				i--;
			}
		}
	}
	
	//Workaround for a NoSuchMethodError.
	public void applyAnimation(AnimationType type, Mesh3D animatable) {
		applyAnimation(type, (Animatable)animatable);
	}

	public void applyAnimation(AnimationType type, Animatable animatable) {
		Animation animation = (Animation) world.resourceCache.getResource(ResourceType.animation, type.toString()).content;
		applyAnimation(animation, animatable);
	}

	public void applyAnimation(Animation animation, Animatable target) {
		AnimationPlayhead playHead = new AnimationPlayhead(animation, target);
		activeAnimations.add(playHead);
		playHead.updateAnimation();
		target.notifyAnimationStart();
	}

}
