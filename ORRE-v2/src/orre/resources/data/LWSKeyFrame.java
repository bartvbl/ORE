package orre.resources.data;

public class LWSKeyFrame {
	// Note that rotations are absolute rotations, in degrees.
	// All values are absolute values the part should have when the animation is complete.
	// For compatability with ORE's animation system these should be converted to relative values by the loader.
	
	public final int frameNumber;
	
	public final double translationX, translationY, translationZ;
	public final double rotationX, rotationY, rotationZ;
	public final double scaleX, scaleY, scaleZ;
	
	public LWSKeyFrame(
			int frameNumber,
			double translationX, double translationY, double translationZ,
			double rotationX, double rotationY, double rotationZ,
			double scaleX, double scaleY, double scaleZ) {
		
		this.frameNumber = frameNumber;
		
		this.translationX = translationX;
		this.translationY = translationY;
		this.translationZ = translationZ;
		
		this.rotationX = rotationX;
		this.rotationY = rotationY;
		this.rotationZ = rotationZ;
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}
}
