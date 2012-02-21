package orre.resources.loaders.obj;

import static org.lwjgl.opengl.GL12.GL_MAX_ELEMENTS_INDICES;
import static org.lwjgl.opengl.GL12.GL_MAX_ELEMENTS_VERTICES;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.partiallyLoadables.UnpackedGeometryBuffer;

public class DataBufferGenerator {
	
	public static void storeDataInVBOs(UnpackedGeometryBuffer buffer) {
		int elementCount = 0;
		FloatBuffer geometryData = BufferUtils.createFloatBuffer(buffer.getVertices().size()*buffer.dataFormat.elementSize);
		IntBuffer indexes = BufferUtils.createIntBuffer(buffer.getVertices().size());
		for(int i = 0; i < buffer.getVertices().size(); i++)
		{
			float[] vertex = buffer.getVertices().get(i);
			addVertexToBuffer(vertex, geometryData, buffer.dataFormat);
			indexes.put(i);
			elementCount++;
			if((elementCount >= GL_MAX_ELEMENTS_VERTICES) || elementCount >= GL_MAX_ELEMENTS_INDICES)
			{
				fillBuffers(geometryData, indexes, buffer);
				elementCount = 0;
			}
		}
		if(elementCount != 0)
		{
			fillBuffers(geometryData, indexes, buffer);
			elementCount = 0;
		}
	}
	
	private static void addVertexToBuffer(float[] vertex, FloatBuffer geometryData, BufferDataFormatType dataFormat) {
		for(float element : vertex)
		{
			geometryData.put(element);
		}
	}
	
	private static void fillBuffers(FloatBuffer geometryData, IntBuffer indexes, UnpackedGeometryBuffer buffer)
	{
		geometryData.rewind();
		indexes.rewind();
		
		int vertexBufferID = VBOUtils.createBuffer();
		int indexBufferID = VBOUtils.createBuffer();
		
		buffer.addVertexBuffer(vertexBufferID, indexBufferID);
		VBOUtils.storeIndexData(indexBufferID, indexes);
		VBOUtils.storeVertexData(vertexBufferID, geometryData);
	}
}
