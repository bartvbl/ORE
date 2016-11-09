package orre.geom;

public class Vertex3D {
	public final float x;
	public final float y;
	public final float z;
	public final float texU;
	public final float texV;
	public final float normX;
	public final float normY;
	public final float normZ;

	public Vertex3D(float x, float y, float z, float texU, float texV, float normX, float normY, float normZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.texU = texU;
		this.texV = texV;
		this.normX = normX;
		this.normY = normY;
		this.normZ = normZ;
	}

	public float[] toArray() {
		return new float[]{x, y, z, texU, texV, normX, normY, normZ};
	}
	
	@Override
	public String toString() {
		return "Vertex3D [" + x + ", " + y + ", " + z + "]";
	}
}
