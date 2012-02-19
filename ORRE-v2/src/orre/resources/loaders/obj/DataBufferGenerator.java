package orre.resources.loaders.obj;

import static org.lwjgl.opengl.GL12.GL_MAX_ELEMENTS_INDICES;
import static org.lwjgl.opengl.GL12.GL_MAX_ELEMENTS_VERTICES;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;

public class DataBufferGenerator {
	private ArrayList<Integer> indexBuffers = new ArrayList<Integer>();
	private ArrayList<Integer> vertexBuffers = new ArrayList<Integer>();
	private int elementCount = 0;
	private int currentBufferIndex = 0;
	private BufferDataFormatType dataFormat;
	
	public void storeDataInVBOs(ArrayList<float[]> vertices, ArrayList<Integer> indices, BufferDataFormatType dataFormat) {
		this.dataFormat = dataFormat;
		FloatBuffer geometryData = BufferUtils.createFloatBuffer(vertices.size()*dataFormat.elementSize);
		IntBuffer indexes = BufferUtils.createIntBuffer(vertices.size());
		for(int i = 0; i < vertices.size(); i++)
		{
			float[] vertex = vertices.get(indices.get(i));
			this.addVertexToBuffer(vertex, geometryData, dataFormat);
			indexes.put(indices.get(i));
			this.elementCount++;
			if((this.elementCount >= GL_MAX_ELEMENTS_VERTICES) || this.elementCount >= GL_MAX_ELEMENTS_INDICES)
			{
				fillBuffers(geometryData, indexes);
			}
		}
		if(this.elementCount != 0)
		{
			fillBuffers(geometryData, indexes);
		}
	}
	
	private void addVertexToBuffer(float[] vertex, FloatBuffer geometryData, BufferDataFormatType dataFormat) {
		for(float element : vertex)
		{
			geometryData.put(element);
		}
	}
	
	private void fillBuffers(FloatBuffer geometryData, IntBuffer indexes)
	{
		geometryData.rewind();
		indexes.rewind();
		
		VBOUtils.storeIndexData(this.vertexBuffers.get(this.currentBufferIndex), indexes);
		VBOUtils.storeVertexData(this.indexBuffers.get(this.currentBufferIndex), geometryData);
		
		this.elementCount = 0;
		this.vertexBuffers.add(VBOUtils.createBuffer());
		this.indexBuffers.add(VBOUtils.createBuffer());
		this.currentBufferIndex ++;
	}
	
	public ArrayList<Integer> getIndexBufferList()
	{
		return this.indexBuffers;
	}
	
	public ArrayList<Integer> getVertexBufferList()
	{
		return this.vertexBuffers;
	}

	public GeometryBuffer getGeometryBuffer() {
		return new GeometryBuffer(this.indexBuffers, this.vertexBuffers, this.dataFormat);
	}
}
