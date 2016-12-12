package orre.resources.data;

public class LWSAnimation {

	public final String partName;
	public final LWSKeyFrame[] frames;
	public final int endBehaviour;

	public LWSAnimation(String partName, LWSKeyFrame[] frames, int endBehaviour) {
		this.partName = partName;
		this.frames = frames;
		this.endBehaviour = endBehaviour;
	}

}
