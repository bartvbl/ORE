package orre.geom.vbo;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

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
		
		return new GeometryNode(indexBufferID, vertexBufferID, dataFormat, vertexCount, DrawingMode.TRIANGLES);
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

	public static GeometryNode generateNormalsGeometryBuffer(BufferDataFormatType dataFormat, DoubleBuffer geometryData, IntBuffer indices) {
		int vertexCount = geometryData.capacity() / dataFormat.elementsPerVertex;
		System.out.println("Building " + vertexCount + " vertices");
		DoubleBuffer vertexBuffer = BufferUtils.createDoubleBuffer(vertexCount * 2 * 3);
		IntBuffer normalIndices = BufferUtils.createIntBuffer(vertexCount * 2 * 2);
		geometryData.rewind();
		for(int i = 0; i < vertexCount; i++) {
			double x = geometryData.get();
			double y = geometryData.get();
			double z = geometryData.get();
			if(dataFormat == BufferDataFormatType.VERTICES_TEXTURES_NORMALS) {
				geometryData.get();
				geometryData.get();
			}
			double nx = geometryData.get();
			double ny = geometryData.get();
			double nz = geometryData.get();
			
			vertexBuffer.put(x);
			vertexBuffer.put(y);
			vertexBuffer.put(z);
			
			vertexBuffer.put(x + nx);
			vertexBuffer.put(y + ny);
			vertexBuffer.put(z + nz);
		}
		
		for(int i = 0; i < vertexCount * 2; i++) {
			normalIndices.put(i);
		}
		
		vertexBuffer.rewind();
		normalIndices.rewind();
		
		int vertexBufferID = createBuffer();
		int indexBufferID = createBuffer();
		
		storeIndexData(indexBufferID, normalIndices);
		storeVertexData(vertexBufferID, vertexBuffer);
		
		return new GeometryNode(indexBufferID, vertexBufferID, BufferDataFormatType.VERTICES, vertexCount*2, DrawingMode.LINES);
	}
}
