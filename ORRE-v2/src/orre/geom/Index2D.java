package orre.geom;

public class Index2D {

	public final int x;
	public final int y;

	public Index2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point2D toPoint2D() {
		return new Point2D(x, y);
	}

}
