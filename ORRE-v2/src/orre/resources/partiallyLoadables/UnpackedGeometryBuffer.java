package orre.resources.partiallyLoadables;

import java.util.Arrays;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.GeometryBufferGenerator;
import orre.resources.loaders.obj.OBJConstants;
import orre.sceneGraph.SceneNode;

public class UnpackedGeometryBuffer extends Finalizable{
	private float[] vertices;
	private BufferDataFormatType dataFormat;
	private int numVertices;
	private int bufferPosition = 0;
	private int numIndicesPerVertex;
	private int numVerticesAdded = 0;
	
	public UnpackedGeometryBuffer(BufferDataFormatType bufferDataFormat, int numVertices) {
		this.dataFormat = bufferDataFormat;
		this.numVertices = numVertices;
		int vertexBufferSize = bufferDataFormat.elementsPerVertex*numVertices;
		this.vertices = new float[vertexBufferSize];
		this.numIndicesPerVertex = bufferDataFormat.elementsPerVertex;
	}
	
	public void addVertex(float[] vertex) {
		//System.out.println("buffer position: " + bufferPosition + ", " + numVerticesAdded);
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

	public float[] getVertices() {
		return this.vertices;
	}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		this.dataFormat = dataType;
	}

	public GeometryBuffer convertToGeometryBuffer() {
		GeometryBuffer buffer = GeometryBufferGenerator.generateGeometryBuffer(this.dataFormat, this.vertices, this.numVertices);
		this.vertices = null;
		return buffer;
	}

	public int getVertexCount() {
		return this.numVertices;
	}

}
