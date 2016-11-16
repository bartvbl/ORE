package orre.gl.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.gl.vao.DrawingMode;
import orre.gl.vao.GeometryBufferGenerator;
import orre.gl.vao.VBOFlags;
import orre.gl.vao.VBOFormat;
import orre.rendering.RenderState;

public class OneShotDrawer {

	public static void drawTriangles(RenderState state, float[] vertexData, int[] indexdata, VBOFormat format) {
		FloatBuffer vertices = BufferUtils.createFloatBuffer(vertexData.length);
		vertices.put(vertexData).rewind();
		IntBuffer indices = BufferUtils.createIntBuffer(indexdata.length);
		indices.put(indexdata).rewind();
		
		GeometryBufferGenerator.generateGeometryBuffer(format, VBOFlags.DYNAMIC_DRAW, vertices, indices, vertexData.length, indexdata.length, DrawingMode.TRIANGLES);
	}

}
