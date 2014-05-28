package openrr.map.loader;

import openrr.map.Orientation;

public class SubTextureCoordinate {

	public final double u1;
	public final double v1;
	public final double u2;
	public final double v2;

	public SubTextureCoordinate(double u1, double v1, double u2, double v2) {
		this.u1 = u1;
		this.v1 = v1;
		this.u2 = u2;
		this.v2 = v2;
	}
	
	public SubTextureCoordinate rotateToRelativeOrientation(Orientation newOrientation) {
		switch(newOrientation) {
		default:
		case north:
			return new SubTextureCoordinate(u1, v1, u2, v2);
		case east:
			return new SubTextureCoordinate(u2, v1, u1, v2);
		case south:
			return new SubTextureCoordinate(u2, v2, u1, v1);
		case west:
			return new SubTextureCoordinate(u1, v2, u2, v1);
		}
	}

}
