package orre.resources.partiallyLoadables;

import java.util.Arrays;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.GeometryBufferGenerator;
import orre.resources.loaders.obj.OBJConstants;
import orre.sceneGraph.SceneNode;

public class UnpackedGeometryBuffer extends Finalizable{
	private double[] vertices;
	private BufferDataFormatType dataFormat;
	private int numVertices;
	private int bufferPosition = 0;
	private int numIndicesPerVertex;
	private int numVerticesAdded = 0;
	
	public UnpackedGeometryBuffer(BufferDataFormatType bufferDataFormat, int numVertices) {
		this.dataFormat = bufferDataFormat;
		this.numVertices = numVertices;
		int vertexBufferSize = bufferDataFormat.elementsPerVertex*numVertices;
		this.vertices = new double[vertexBufferSize];
		this.numIndicesPerVertex = bufferDataFormat.elementsPerVertex;
	}
	
	public void addVertex(double[] vertex) {
		for(int i = 0; i < numIndicesPerVertex; i++) {			
			this.vertices[bufferPosition + i] = vertex[i];
		}
		this.numVerticesAdded++;
		this.bufferPosition += numIndicesPerVertex;
	}

	public void finalizeResource() {}

	@Override
	public SceneNode createSceneNode() {
		return null;
	}

	public void addToCache() {}

	public double[] getVertices() {
		return this.vertices;
	}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		this.dataFormat = dataType;
	}

	public GeometryBuffer convertToGeometryBuffer() {
		int[] indices = new int[this.numVertices];
		for(int i = 0; i < this.numVertices; i++) indices[i] = i;
		GeometryBuffer buffer = GeometryBufferGenerator.generateGeometryBuffer(this.dataFormat, this.vertices, indices);
		this.vertices = null;
		return buffer;
	}

	public int getVertexCount() {
		return this.numVertices;
	}

}
