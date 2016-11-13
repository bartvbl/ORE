package orre.gl.vao;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

public class GeometryBuffer {
	private final int vertexCount;
	private final int indexCount;
	
	private final DoubleBuffer geometryBuffer;
	private final IntBuffer indexBuffer;

	public GeometryBuffer(int vertexCount, int indexCount, VBOFormat dataFormat) {
		this.vertexCount = vertexCount;
		this.indexCount = indexCount;
		
		this.geometryBuffer = DoubleBuffer.allocate(dataFormat.elementsPerVertex * vertexCount);
		this.indexBuffer = IntBuffer.allocate(indexCount);
	}
	
	public void putVertex(DoubleBuffer vertex) {
		geometryBuffer.put(vertex);
	}
	
	public void putIndex(int index) {
		this.indexBuffer.put(index);
	}
	
	public void putIndices(IntBuffer indices) {
		this.indexBuffer.put(indices);
	}
}
