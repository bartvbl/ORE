package orre.resources.partiallyLoadables;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryNode;
import orre.geom.vbo.GeometryBufferGenerator;
import orre.resources.Finalizable;
import orre.resources.ResourceCache;
import orre.sceneGraph.SceneNode;

public class UnpackedGeometryBuffer extends Finalizable{
	private DoubleBuffer vertices;
	private BufferDataFormatType dataFormat;
	private int numVertices;
	
	public UnpackedGeometryBuffer(BufferDataFormatType bufferDataFormat, int numVertices) {
		this.dataFormat = bufferDataFormat;
		this.numVertices = numVertices;
		int vertexBufferSize = bufferDataFormat.elementsPerVertex*numVertices;
		this.vertices = BufferUtils.createDoubleBuffer(vertexBufferSize);
	}
	
	public void addVertex(DoubleBuffer vertex) {
		vertices.put(vertex);
	}

	public void finalizeResource() {}

	@Override
	public SceneNode createSceneNode() {
		return null;
	}

	public void addToCache(ResourceCache cache) {}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		this.dataFormat = dataType;
	}

	public GeometryNode convertToGeometryBuffer() {
		IntBuffer indices = BufferUtils.createIntBuffer(this.numVertices);
		for(int i = 0; i < this.numVertices; i++) indices.put(i);
		GeometryNode buffer = GeometryBufferGenerator.generateGeometryBuffer(this.dataFormat, vertices, indices);
		this.vertices = null;
		return buffer;
	}

	public int getVertexCount() {
		return this.numVertices;
	}

}
