package orre.gl.vao;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GeometryBufferGenerator {
	
	public static GeometryNode generateGeometryBuffer(VBOFormat dataFormat, FloatBuffer geometryData, IntBuffer indices, int vertexCount, int indexCount) {
		return generateGeometryBuffer(dataFormat, VBOFlags.STATIC_DRAW, geometryData, indices, vertexCount, indexCount, DrawingMode.TRIANGLES);
	}
	
	public static GeometryNode generateGeometryBuffer(VBOFormat dataFormat, VBOFlags flags, FloatBuffer geometryData, IntBuffer indices, int vertexCount, int indexCount, DrawingMode drawingMode) {
		int arrayID = glGenVertexArrays();
		glBindVertexArray(arrayID);
		
		geometryData.rewind();
		indices.rewind();
		
		int vertexBufferID = createBuffer();
		int indexBufferID = createBuffer();
		
		storeIndexData(indexBufferID, indices, flags);
		storeVertexData(vertexBufferID, geometryData, flags);
		
		// Since all data is stored in single buffers, we don't need to bind other buffers for setting these up
		setDataPointers(dataFormat);
		
		return new GeometryNode(arrayID, indexCount, vertexCount, drawingMode, new int[]{vertexBufferID, indexBufferID});
	}

	private static void setDataPointers(VBOFormat dataFormat) {
		int bytesPerFloat = 4;
		
		switch(dataFormat) {
		case VERTICES:
			int stride = 3 * bytesPerFloat;
			glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
			
			glEnableVertexAttribArray(0);
			
			break;
		case VERTICES_AND_NORMALS:
			stride = (3 + 3) * bytesPerFloat;
			glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
			glVertexAttribPointer(2, 3, GL_FLOAT, true,  stride, 3 * bytesPerFloat);
			
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(2);
			
			break;
		case VERTICES_AND_TEXTURES:
			stride = (2 + 3) * bytesPerFloat;
			glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 3 * bytesPerFloat);
			
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			
			break;
		case VERTICES_TEXTURES_NORMALS:
			stride = (2 + 3 + 3) * bytesPerFloat;
			glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 3 * bytesPerFloat);
			glVertexAttribPointer(2, 3, GL_FLOAT, true,  stride, (2 + 3) * bytesPerFloat);
			
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glEnableVertexAttribArray(2);
			
			break;
		default:
			throw new RuntimeException("Unsupported buffer data format");
		}
	}

	private static int createBuffer() {
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		glGenBuffers(buffer);
		return buffer.get(0);
	}
	
	private static void storeVertexData(int bufferIndex, FloatBuffer geometryData, VBOFlags flags) {
		int hint = GL_STATIC_DRAW;
		if(flags == VBOFlags.DYNAMIC_DRAW) {
			hint = GL_DYNAMIC_DRAW;
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, bufferIndex);
		glBufferData(GL_ARRAY_BUFFER, geometryData, hint);
	}
	
	private static void storeIndexData(int bufferIndex, IntBuffer indexes, VBOFlags flags) {
		int hint = GL_STATIC_DRAW;
		if(flags == VBOFlags.DYNAMIC_DRAW) {
			hint = GL_DYNAMIC_DRAW;
		}
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferIndex);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, hint);
	}
	
	public static GeometryNode generateNormalsGeometryBuffer(VBOFormat dataFormat, FloatBuffer geometryData, IntBuffer indices) {
		int vertexCount = geometryData.capacity() / dataFormat.elementsPerVertex;
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexCount * 2 * 3);
		IntBuffer normalIndices = BufferUtils.createIntBuffer(vertexCount * 2 * 2);
		geometryData.rewind();
		for(int i = 0; i < vertexCount; i++) {
			float x = geometryData.get();
			float y = geometryData.get();
			float z = geometryData.get();
			if(dataFormat == VBOFormat.VERTICES_TEXTURES_NORMALS) {
				geometryData.get();
				geometryData.get();
			}
			float nx = geometryData.get();
			float ny = geometryData.get();
			float nz = geometryData.get();
			
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
		
		return generateGeometryBuffer(dataFormat, VBOFlags.STATIC_DRAW, vertexBuffer, normalIndices, vertexCount * 2 * 2, vertexCount*2, DrawingMode.LINES);
	}

}
