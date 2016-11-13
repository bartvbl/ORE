package orre.geom;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.gl.vao.GeometryBufferGenerator;
import orre.gl.vao.GeometryNode;
import orre.gl.vao.VBOFormat;

public class Shapes {

	public static GeometryNode generateTexturedSquare() {
		int vertexCount = (3 + 2) * 3 * 2;
		int indexCount = 6;
		
		FloatBuffer geometry = BufferUtils.createFloatBuffer(vertexCount * 3);
		IntBuffer indices = BufferUtils.createIntBuffer(indexCount);
		
		geometry.put(new float[]{
			0.0f, 0.0f, 0.0f,	0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,	1.0f, 0.0f,
			1.0f, 1.0f, 0.0f,	1.0f, 1.0f,
			
			0.0f, 0.0f, 0.0f,	0.0f, 0.0f,
			1.0f, 1.0f, 0.0f,	1.0f, 1.0f,
			0.0f, 1.0f, 0.0f, 	0.0f, 1.0f
			
		});
		indices.put(new int[]{
				0, 1, 2, 3, 4, 5
		});
		
		return GeometryBufferGenerator.generateGeometryBuffer(VBOFormat.VERTICES_AND_TEXTURES, geometry, indices, vertexCount, indexCount);
	}

}
