package orre.resources.partiallyLoadables;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.geom.vbo.VBOFormat;
import orre.geom.vbo.GeometryNode;
import orre.geom.vbo.GeometryBufferGenerator;
import orre.resources.Finalizable;
import orre.resources.Resource;

public class UnpackedGeometryBuffer implements Finalizable{
	private DoubleBuffer vertices;
	private VBOFormat dataFormat;
	private final int numVertices;
	
	public UnpackedGeometryBuffer(VBOFormat bufferDataFormat, int numVertices) {
		this.dataFormat = bufferDataFormat;
		this.numVertices = numVertices;
		int vertexBufferSize = bufferDataFormat.elementsPerVertex*numVertices;
		this.vertices = BufferUtils.createDoubleBuffer(vertexBufferSize);
	}
	
	public void addVertex(DoubleBuffer vertex) {
		vertices.put(vertex);
	}

	@Override
	public Resource finalizeResource() {
		return null;
	}

	public void setBufferDataFormat(VBOFormat dataType) {
		this.dataFormat = dataType;
	}

	public GeometryNode convertToGeometryBuffer() {
		IntBuffer indices = BufferUtils.createIntBuffer(this.numVertices);
		for(int i = 0; i < this.numVertices; i++) indices.put(i);
		GeometryNode buffer = GeometryBufferGenerator.generateGeometryBuffer(this.dataFormat, vertices, indices, numVertices, numVertices);
		this.vertices = null;
		return buffer;
	}
}
