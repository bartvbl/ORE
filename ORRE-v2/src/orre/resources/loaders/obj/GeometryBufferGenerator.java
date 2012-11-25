package orre.resources.loaders.obj;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBuffer;
import orre.resources.partiallyLoadables.UnpackedGeometryBuffer;

public class GeometryBufferGenerator {
	
	public static GeometryBuffer generateGeometryBuffer(BufferDataFormatType dataFormat, float[] vertices, int[] indices) {
		int elementsPerVertex = dataFormat.elementsPerVertex;
		int vertexCount = vertices.length / elementsPerVertex;
		
		FloatBuffer geometryData = BufferUtils.createFloatBuffer(vertices.length);
		IntBuffer indexes = BufferUtils.createIntBuffer(indices.length);
		
		geometryData.put(vertices);
		indexes.put(indexes);
		
		GeometryBuffer geometryBuffer = storeBuffersInVRAM(geometryData, indexes, dataFormat, vertexCount);
		
		return geometryBuffer;
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
