package orre.animation;

import orre.resources.IncompleteResourceObject;
import orre.resources.ResourceObject;

public class Animation implements IncompleteResourceObject<Animation>, ResourceObject<Animation> {

	public final KeyFrame[] keyFrames;
	public final String type;

	public Animation(Enum<?> type, KeyFrame[] keyFrames) {
		this(type.toString(), keyFrames);
	}
	
	public Animation(String type, KeyFrame[] keyFrames) {
		this.keyFrames = keyFrames;
		this.type = type;
	}

	public Animation createInstance() {
		return this;
	}
}
