package orre.gl.vertexArrays;

import static org.lwjgl.opengl.GL11.*;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.geom.vbo.BufferDataFormatType;
import orre.geom.vbo.GeometryBufferGenerator;
import orre.geom.vbo.GeometryNode;

public class VertexArrayDrawer {
	
	//If you get a buffer overflow error, up the byte count reserved for the buffer.
	//The class is meant to do immediate-mode ish drawing for small batches of geometry.
	
	private static final DoubleBuffer vertexBuffer = BufferUtils.createDoubleBuffer(1024);
	private static final IntBuffer indexBuffer = BufferUtils.createIntBuffer(256);
	
	public static void drawTriangles(double[] vertices, int[] indices, int axisCount, int vertexCount) {
		vertexBuffer.put(vertices).flip();
		indexBuffer.put(indices).flip();
		//GeometryNode tmpNode = GeometryBufferGenerator.generateGeometryBuffer(BufferDataFormatType.VERTICES, vertexBuffer, indexBuffer, vertexCount, indices.length);
		//tmpNode.render();
		//tmpNode.destroy();
		//draw(GL_TRIANGLES, vertices.length / 3, vertices, axisCount);
	}

	public static void drawQuads(double[] vertices, int axisCount) {
		draw(GL_QUADS, vertices.length / 4, vertices, axisCount);
	}
	
	private static void draw(int glEnum, int vertexCount, double[] vertices, int axisCount) {
		vertexBuffer.rewind();
		vertexBuffer.put(vertices);
		vertexBuffer.flip();
		
		glEnableClientState(GL_VERTEX_ARRAY);         
		glVertexPointer(axisCount, 3 * axisCount, vertexBuffer);
		glDrawArrays(glEnum, 0, vertexCount);
		glDisableClientState(GL_VERTEX_ARRAY);
	}
}
