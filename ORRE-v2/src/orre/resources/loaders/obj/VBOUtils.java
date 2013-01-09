package orre.resources.loaders.obj;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GLContext;

public class VBOUtils {
	
	public static int createBuffer() {
		if (supportsBuffers()) 
		{
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			ARBVertexBufferObject.glGenBuffersARB(buffer);
			return buffer.get(0);
		}
		return 0;
	}
	
	public static void storeVertexData(int bufferIndex, DoubleBuffer geometryData) {
		if (supportsBuffers()) 
		{
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, bufferIndex);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, geometryData, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}
	}
	
	public static void storeIndexData(int bufferIndex, IntBuffer indexes) {
		if (supportsBuffers()) 
		{
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, bufferIndex);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indexes, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}
	}
	
	public static void destroyBuffers(ArrayList<Integer> bufferList) {
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
