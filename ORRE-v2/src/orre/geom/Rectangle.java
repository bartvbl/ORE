package orre.geom;

public class Rectangle {
	public final double x1;
	public final double y1;
	public final double x2;
	public final double y2;
	public final double width;
	public final double height;
	
	public Rectangle(double x, double y, double width, double height) {
		this.x1 = x;
		this.x2 = x1 + width;
		this.y1 = y;
		this.y2 = y1 + height;
		this.width = width;
		this.height = height;
	}
}
