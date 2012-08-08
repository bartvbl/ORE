package orre.resources.partiallyLoadables;

import java.util.ArrayList;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.DataBufferGenerator;
import orre.sceneGraph.SceneNode;

public class UnpackedGeometryBuffer extends Finalizable{
	private int vertexBuffer = 0;
	private int indexBuffer = 0;
	
	private ArrayList<float[]> vertices = new ArrayList<float[]>();
	public BufferDataFormatType dataFormat;
	private int numVertices;
	
	public UnpackedGeometryBuffer(BufferDataFormatType bufferDataFormat) {
		this.dataFormat = bufferDataFormat;
	}
	
	public void addVertex(float[] vertex) {
		this.vertices.add(vertex);
	}

	public void finalizeResource() {
		DataBufferGenerator.storeDataInVBOs(this);
		this.numVertices = this.vertices.size();
		this.vertices = null;
	}

	@Override
	public SceneNode createSceneNode() {
		
		return null;
	}

	public void addToCache() {}

	public boolean isEmpty() {
		return this.vertices.isEmpty();
	}

	public ArrayList<float[]> getVertices() {
		return this.vertices;
	}

	public void addVertexBuffer(int vertexBufferID, int indexBufferID) {
//		this.vertexBuffers.add(vertexBufferID);
//		this.indices.add(indexBufferID);
	}
	
	public GeometryBuffer convertToGeometryBuffer()
	{
		return new GeometryBuffer(this.indexBuffer, this.vertexBuffer, this.dataFormat, this.numVertices);
	}

	public void setBufferDataFormat(BufferDataFormatType dataType) {
		System.out.println("UPDATING DATA TYPE");
		this.dataFormat = dataType;
	}

}
