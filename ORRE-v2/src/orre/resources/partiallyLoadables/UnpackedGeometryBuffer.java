package orre.resources.partiallyLoadables;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.gl.vao.GeometryBufferGenerator;
import orre.gl.vao.GeometryNode;
import orre.gl.vao.VBOFormat;
import orre.resources.Finalizable;
import orre.resources.Resource;

public class UnpackedGeometryBuffer implements Finalizable{
	private FloatBuffer vertices;
	private VBOFormat dataFormat;
	private final int numVertices;
	
	public UnpackedGeometryBuffer(VBOFormat bufferDataFormat, int numVertices) {
		this.dataFormat = bufferDataFormat;
		this.numVertices = numVertices;
		int vertexBufferSize = bufferDataFormat.elementsPerVertex*numVertices;
		this.vertices = BufferUtils.createFloatBuffer(vertexBufferSize);
	}
	
	public void addVertex(FloatBuffer vertex) {
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
