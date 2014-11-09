package orre.gameWorld.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.lwjgl.util.Timer;

import orre.animation.Animatable;
import orre.animation.Animation;
import orre.animation.AnimationType;
import orre.animation.execution.AnimationPlayhead;
import orre.gameWorld.core.GameWorld;
import orre.gameWorld.core.Message;
import orre.gameWorld.core.MessageType;
import orre.geom.mesh.Mesh3D;
import orre.resources.ResourceType;

public class AnimationService implements Service {
	private final GameWorld world;
	private final HashMap<Integer, AnimationPlayhead> activeAnimations = new HashMap<Integer, AnimationPlayhead>();
	private final ArrayList<Integer> activeAnimationIDs = new ArrayList<Integer>();

	public AnimationService(GameWorld world) {
		this.world = world;
	}
	
	@Override
	public void tick() {
		Timer.tick();
		for(int i = activeAnimationIDs.size() - 1; i >= 0; i--) {
			int animationID = activeAnimationIDs.get(i);
			AnimationPlayhead playHead = activeAnimations.get(animationID);
			try {
				playHead.updateAnimation();
			} catch(NullPointerException e) {
				stopAnimation(animationID);
				System.err.println("Animation caused an error. Aborting. Message: " + e.getMessage());
			}
		}
		for(int i = 0; i < activeAnimationIDs.size(); i++) {
			int animationID = activeAnimationIDs.get(i);
			AnimationPlayhead playHead = activeAnimations.get(animationID);
			if(playHead.isFinished()) {
				stopAnimation(playHead.id);
				i--;
			}
		}
	}

	//Workaround for a NoSuchMethodError.
	public int applyAnimation(AnimationType type, Mesh3D animatable) {
		return applyAnimation(type, (Animatable)animatable);
	}

	public int applyAnimation(AnimationType type, Animatable animatable) {
		Animation animation = (Animation) world.resourceCache.getResource(ResourceType.animation, type.toString()).content;
		return applyAnimation(animation, animatable);
	}

	public int applyAnimation(Animation animation, Animatable target) {
		AnimationPlayhead playHead = new AnimationPlayhead(animation, target);
		activeAnimations.put(playHead.id, playHead);
		activeAnimationIDs.add(playHead.id);
		playHead.updateAnimation();
		//TODO: dispatch animation start message
		return playHead.id;
	}
	
	public void stopAnimation(int animationID) {
		activeAnimations.remove(animationID);
		activeAnimationIDs.remove((Integer)animationID); //cast to avoid (int index) overload
		this.world.dispatchMessage(new Message<Integer>(MessageType.ANIMATION_ENDED, animationID));
	}
	
	

}
