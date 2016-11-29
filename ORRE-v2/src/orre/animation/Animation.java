package orre.animation;

import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceType;

public class Animation implements Finalizable {

	public final KeyFrame[] keyFrames;
	public final AnimationType type;

	public Animation(AnimationType type, KeyFrame[] keyFrames) {
		this.keyFrames = keyFrames;
		this.type = type;
	}

	@Override
	public Resource finalizeResource() {
		return new Resource(ResourceType.animation, type.toString(), Animation.class, this);
	}
	
}
