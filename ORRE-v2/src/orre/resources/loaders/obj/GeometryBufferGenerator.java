package orre.resources.loaders.obj;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.partiallyLoadables.UnpackedGeometryBuffer;

public class GeometryBufferGenerator {
	
	public static GeometryBuffer generateGeometryBuffer(BufferDataFormatType dataFormat, float[] vertices, int vertexCount) {
		int elementsPerVertex = dataFormat.elementsPerVertex;
		FloatBuffer geometryData = BufferUtils.createFloatBuffer(vertexCount*elementsPerVertex);
		IntBuffer indexes = BufferUtils.createIntBuffer(vertexCount);
		
		putVerticesInBuffers(vertices, geometryData, indexes, elementsPerVertex);
		
		GeometryBuffer geometryBuffer = storeBuffersInVRAM(geometryData, indexes, dataFormat, vertexCount);
		
		return geometryBuffer;
	}
	
	private static void putVerticesInBuffers(float[] vertices, FloatBuffer geometryData, IntBuffer indexes, int elementsPerVertex) {
//		System.out.println(Arrays.toString(vertices));
		int vertexCount = 0;
		for(int vertexStartIndex = 0; vertexStartIndex < vertices.length; vertexStartIndex += elementsPerVertex) {
			indexes.put(vertexCount);
			vertexCount++;
			for(int vertexIndex = vertexStartIndex; vertexIndex < vertexStartIndex + elementsPerVertex; vertexIndex++) {
				geometryData.put(vertices[vertexIndex]);
			}
		}
	}

	private static GeometryBuffer storeBuffersInVRAM(FloatBuffer geometryData, IntBuffer indexes, BufferDataFormatType dataFormat, int vertexCount)
	{
		geometryData.rewind();
		indexes.rewind();
		
		int vertexBufferID = VBOUtils.createBuffer();
		int indexBufferID = VBOUtils.createBuffer();
		
		VBOUtils.storeIndexData(indexBufferID, indexes);
		VBOUtils.storeVertexData(vertexBufferID, geometryData);
		
		return new GeometryBuffer(indexBufferID, vertexBufferID, dataFormat, vertexCount);
	}
}
