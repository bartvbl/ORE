package orre.geom;

public class Point2D {
	public final int x;
	public final int y;

	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "("+x+", " +y+")";
	}
}
