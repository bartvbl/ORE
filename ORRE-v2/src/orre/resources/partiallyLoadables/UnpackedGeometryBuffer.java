package orre.resources.partiallyLoadables;

import java.nio.DoubleBuffer;
import java.util.Arrays;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.geom.vbo.GeometryBufferGenerator;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.OBJConstants;
import orre.sceneGraph.SceneNode;

public class UnpackedGeometryBuffer extends Finalizable{
	private DoubleBuffer vertices;
	private BufferDataFormatType dataFormat;
	private int numVertices;
	private int numIndicesPerVertex;
	
	public UnpackedGeometryBuffer(BufferDataFormatType bufferDataFormat, int numVertices) {
		this.dataFormat = bufferDataFormat;
		this.numVertices = numVertices;
		int vertexBufferSize = bufferDataFormat.elementsPerVertex*numVertices;
		this.vertices = DoubleBuffer.allocate(vertexBufferSize);
		this.numIndicesPerVertex = bufferDataFormat.elementsPerVertex;
	}
	
	public void addVertex(DoubleBuffer vertex) {
		vertices.put(vertex);
	}

	public void finalizeResource() {}

	@Override
	public SceneNode createSceneNode() {
		return null;
	}

	public void addToCache() {}

	public double[] getVertices() {
		double[] allVertices = new double[vertices.capacity()];
		int position = vertices.position();
		vertices.position(0);
		vertices.get(allVertices);
		vertices.position(position);
		return allVertices;
	}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		this.dataFormat = dataType;
	}

	public GeometryBuffer convertToGeometryBuffer() {
		int[] indices = new int[this.numVertices];
		for(int i = 0; i < this.numVertices; i++) indices[i] = i;
		GeometryBuffer buffer = GeometryBufferGenerator.generateGeometryBuffer(this.dataFormat, getVertices(), indices);
		this.vertices = null;
		return buffer;
	}

	public int getVertexCount() {
		return this.numVertices;
	}

}
