package orre.geom;

public class Vector3D {
	public final float x;
	public final float y;
	public final float z;
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(float[] vector) {
		this.x = vector[0];
		this.y = vector[1];
		this.z = vector[2];
	}
	
	public double scalarProduct(Vector3D vector) {
		return this.x*vector.x + this.y*vector.y + this.z*vector.z;
	}
	
	public Vector3D vectorProduct(Vector3D vector) {
		float x = (this.y*vector.z) - (this.z*vector.y);
		float y = (this.z*vector.x) - (this.x*vector.z);
		float z = (this.x*vector.y) - (this.y*vector.x);
		return new Vector3D(x, y, z);
	}
	
	public double getLength() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3D normalize() {
		float length = (float) this.getLength();
		float x = this.x/length;
		float y = this.y/length;
		float z = this.z/length;
		return new Vector3D(x, y, z);
	}
	
	@Override
	public String toString() {
		return "vert3D ["+x+", "+y+", "+z+"]";
	}

	public Vector3D inverse() {
		return new Vector3D(-x, -y, -z);
	}

	public Vector3D minus(Vector3D other) {
		return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	public Vector3D plus(Vector3D other) {
		return new Vector3D(x + other.x, y + other.y, z + other.z);
	}
}
