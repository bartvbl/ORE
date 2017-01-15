package orre.animation;

import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceType;

public class Animation implements Finalizable {

	public final KeyFrame[] keyFrames;
	public final String type;

	public Animation(Enum<?> type, KeyFrame[] keyFrames) {
		this(type.toString(), keyFrames);
	}
	
	public Animation(String type, KeyFrame[] keyFrames) {
		this.keyFrames = keyFrames;
		this.type = type;
	}

	@Override
	public Resource finalizeResource() {
		return new Resource(ResourceType.animation, type, Animation.class);
	}
	
}
