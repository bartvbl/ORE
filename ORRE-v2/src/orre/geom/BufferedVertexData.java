package orre.geom;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GLContext;
import static org.lwjgl.opengl.GL12.*;

public class BufferedVertexData {
	protected ArrayList<float[]> vertices = new ArrayList<float[]>();
	private int vertexCount = 0;
	private int currentBufferIndex = 0;
	private boolean isPacked = false;
	
	private ArrayList<Integer> vertElements = new ArrayList<Integer>();
	private ArrayList<Integer> vertexBuffers = new ArrayList<Integer>();
	
	private static final int MAX_ITEMS = Math.min(GL_MAX_ELEMENTS_VERTICES, GL_MAX_ELEMENTS_INDICES);
	
	public BufferedVertexData()
	{
		this.vertexBuffers.add(this.createBuffer());
		this.vertElements.add(this.createBuffer());
	}
	public void destroy()
	{
		IntBuffer vertexBuffers = BufferUtils.createIntBuffer(this.vertexBuffers.size());
		for(int i = 0; i < this.vertexBuffers.size(); i++)
		{
			vertexBuffers.put(this.vertexBuffers.get(i));
		}
		vertexBuffers.rewind();
		
		IntBuffer vertElementsBuffers = BufferUtils.createIntBuffer(this.vertElements.size());
		for(int i = 0; i < this.vertElements.size(); i++)
		{
			vertElementsBuffers.put(this.vertElements.get(i));
		}
		vertElementsBuffers.rewind();
		
		ARBVertexBufferObject.glDeleteBuffersARB(vertexBuffers);
		ARBVertexBufferObject.glDeleteBuffersARB(vertElementsBuffers);
	}
	public void addVertex(float x, float y, float z, float texX, float texY, float normalX, float normalY, float normalZ)
	{
		this.vertices.add(new float[] {x, y, z, normalX, normalY, normalZ, texX, texY});
	}
	public void pack()
	{	
		if(this.isPacked)
		{
			System.out.println("ERROR: tried to repack a BufferedVertexData instance");
			return;
		}
		this.isPacked = true;
		FloatBuffer geometryData = BufferUtils.createFloatBuffer(this.vertices.size()*8);
		IntBuffer indexes = BufferUtils.createIntBuffer(this.vertices.size());
		float[] vertex;
		for(int i = 0; i < this.vertices.size(); i++)
		{
			vertex = this.vertices.get(i);
			geometryData.put(vertex[0]).put(vertex[1]).put(vertex[2]).put(vertex[3]).put(vertex[4]).put(vertex[5]).put(vertex[6]).put(vertex[7]);
			indexes.put(vertexCount);
			this.vertexCount++;
			if(this.vertexCount >= BufferedVertexData.MAX_ITEMS)
			{
				this.fillBuffers(geometryData, indexes);
			}
		}
		if(this.vertexCount != 0)
		{
			return;
		}
		this.fillBuffers(geometryData, indexes);
	}
	private void fillBuffers(FloatBuffer geometryData, IntBuffer indexes)
	{
		geometryData.rewind();
		indexes.rewind();
		
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) 
		{
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertexBuffers.get(this.currentBufferIndex));
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, geometryData, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, this.vertElements.get(this.currentBufferIndex));
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indexes, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}
		
		this.vertexCount = 0;
		this.vertexBuffers.add(this.createBuffer());
		this.vertElements.add(this.createBuffer());
		this.currentBufferIndex++;
	}
	
	private int createBuffer() 
	{
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) 
		{
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			ARBVertexBufferObject.glGenBuffersARB(buffer);
			return buffer.get(0);
		}
		return 0;
	}
}
