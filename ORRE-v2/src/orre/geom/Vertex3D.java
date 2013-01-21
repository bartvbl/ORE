package orre.geom;

public class Vertex3D {
	public final double x;
	public final double y;
	public final double z;
	public final double texU;
	public final double texV;
	public final double normX;
	public final double normY;
	public final double normZ;

	public Vertex3D(double x, double y, double z, double texU, double texV, double normX, double normY, double normZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.texU = texU;
		this.texV = texV;
		this.normX = normX;
		this.normY = normY;
		this.normZ = normZ;
	}

	public double[] toArray() {
		return new double[]{x, y, z, texU, texV, normX, normY, normZ};
	}
}
