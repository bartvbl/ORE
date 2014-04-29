package orre.geom;

public class Vector2D {
	public final double x;
	public final double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(double[] vector) {
		this.x = vector[0];
		this.y = vector[1];
	}
	
	public double scalarProduct(Vector2D vector) {
		return this.x*vector.x + this.y*vector.y;
	}
	
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector2D normalize() {
		double length = this.length();
		double x = this.x/length;
		double y = this.y/length;
		return new Vector2D(x, y);
	}
	
	public String toString() {
		return "vec2D ["+x+", "+y+"]";
	}

	public Vector2D inverse() {
		return new Vector2D(-x, -y);
	}

	public Vector2D minus(Vector2D other) {
		return new Vector2D(this.x - other.x, this.y - other.y);
	}
}
