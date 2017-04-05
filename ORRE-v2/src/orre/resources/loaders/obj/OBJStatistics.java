package orre.resources.loaders.obj;

public class OBJStatistics {

	public final int vertexCount;
	public final int normalCount;
	public final int texCoordCount;
	public final int faceCount;

	public OBJStatistics(int vertexCount, int normalCount, int texCoordCount, int faceCount) {
		this.vertexCount = vertexCount;
		this.normalCount = normalCount;
		this.texCoordCount = texCoordCount;
		this.faceCount = faceCount;
	}

}
