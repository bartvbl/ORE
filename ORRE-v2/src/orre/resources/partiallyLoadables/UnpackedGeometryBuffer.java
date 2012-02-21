package orre.resources.partiallyLoadables;

import java.util.ArrayList;

import orre.geom.vbo.BufferDataFormatType;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.DataBufferGenerator;
import orre.sceneGraph.SceneNode;

public class UnpackedGeometryBuffer extends Finalizable{
	private ArrayList<Integer> vertexBuffers = new ArrayList<Integer>();
	private ArrayList<Integer> indices = new ArrayList<Integer>();
	
	private ArrayList<float[]> vertices = new ArrayList<float[]>();
	public final BufferDataFormatType dataFormat;
	
	public UnpackedGeometryBuffer(BufferDataFormatType bufferDataFormat) {
		this.dataFormat = bufferDataFormat;
	}
	
	public void addVertex(float[] vertex) {
		this.vertices.add(vertex);
	}

	public void finalizeResource() {
		DataBufferGenerator.storeDataInVBOs(this);
		this.vertices = null;
	}

	@Override
	public SceneNode createSceneNode() {
		// TODO Auto-generated method stub
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
		this.vertexBuffers.add(vertexBufferID);
		this.indices.add(indexBufferID);
	}

}