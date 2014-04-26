package orre.geom.vbo;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GLContext;

public class GeometryBufferGenerator {
	
	public static GeometryNode generateGeometryBuffer(BufferDataFormatType dataFormat, DoubleBuffer geometryData, IntBuffer indices) {
		int vertexCount = geometryData.capacity() / dataFormat.elementsPerVertex;
		
		GeometryNode geometryBuffer = storeBuffersInVRAM(geometryData, indices, dataFormat, vertexCount);
		
		return geometryBuffer;
	}

	private static GeometryNode storeBuffersInVRAM(DoubleBuffer geometryData, IntBuffer indexes, BufferDataFormatType dataFormat, int vertexCount)
	{
		geometryData.rewind();
		indexes.rewind();
		
		int vertexBufferID = createBuffer();
		int indexBufferID = createBuffer();
		
		storeIndexData(indexBufferID, indexes);
		storeVertexData(vertexBufferID, geometryData);
		
		return new GeometryNode(indexBufferID, vertexBufferID, dataFormat, vertexCount);
	}
	
	private static int createBuffer() {
		if (supportsBuffers()) 
		{
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			ARBVertexBufferObject.glGenBuffersARB(buffer);
			return buffer.get(0);
		}
		return 0;
	}
	
	private static void storeVertexData(int bufferIndex, DoubleBuffer geometryData) {
		if (supportsBuffers()) 
		{
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, bufferIndex);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, geometryData, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}
	}
	
	private static void storeIndexData(int bufferIndex, IntBuffer indexes) {
		if (supportsBuffers()) 
		{
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, bufferIndex);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indexes, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}
	}
	
	private static void destroyBuffers(ArrayList<Integer> bufferList) {
		if(!supportsBuffers())
		{
			return;
		}
		IntBuffer bufferIDBuffer = BufferUtils.createIntBuffer(bufferList.size());
		for(int i = 0; i < bufferList.size(); i++)
		{
			bufferIDBuffer.put(bufferList.get(i));
		}
		bufferIDBuffer.rewind();
		ARBVertexBufferObject.glDeleteBuffersARB(bufferIDBuffer);
	}
	
	private static boolean supportsBuffers()
	{
		return GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
	}
}
