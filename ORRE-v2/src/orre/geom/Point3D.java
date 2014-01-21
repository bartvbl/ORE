package orre.geom;

public class Point3D {
	public final double x;
	public final double y;
	public final double z;

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point2D in2D() {
		return new Point2D((int)x, (int)y);
	}
}
