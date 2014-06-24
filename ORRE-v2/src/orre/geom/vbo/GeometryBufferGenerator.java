package orre.geom.vbo;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.*;
import org.lwjgl.opengl.GLContext;

public class GeometryBufferGenerator {
	
	public static GeometryNode generateGeometryBuffer(BufferDataFormatType dataFormat, DoubleBuffer geometryData, IntBuffer indices, int vertexCount, int indexCount) {
		
		GeometryNode geometryBuffer = storeBuffersInVRAM(geometryData, indices, dataFormat, vertexCount, indexCount);
		
		return geometryBuffer;
	}

	private static GeometryNode storeBuffersInVRAM(DoubleBuffer geometryData, IntBuffer indexes, BufferDataFormatType dataFormat, int vertexCount, int indexCount)
	{
		geometryData.rewind();
		indexes.rewind();
		
		int vertexBufferID = createBuffer();
		int indexBufferID = createBuffer();
		
		storeIndexData(indexBufferID, indexes);
		storeVertexData(vertexBufferID, geometryData);
		
		return new GeometryNode(indexBufferID, vertexBufferID, dataFormat, vertexCount, indexCount, DrawingMode.TRIANGLES);
	}
	
	private static int createBuffer() {
		if (supportsBuffers()) 
		{
			IntBuffer buffer = BufferUtils.createIntBuffer(1);
			glGenBuffers(buffer);
			return buffer.get(0);
		}
		return 0;
	}
	
	private static void storeVertexData(int bufferIndex, DoubleBuffer geometryData) {
		if (supportsBuffers()) 
		{
			glBindBuffer(GL_ARRAY_BUFFER, bufferIndex);
			glBufferData(GL_ARRAY_BUFFER, geometryData, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
	}
	
	private static void storeIndexData(int bufferIndex, IntBuffer indexes) {
		if (supportsBuffers()) 
		{
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferIndex);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
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
		
		return new GeometryNode(indexBufferID, vertexBufferID, BufferDataFormatType.VERTICES, vertexCount*2, vertexCount * 2 * 2, DrawingMode.LINES);
	}
}
