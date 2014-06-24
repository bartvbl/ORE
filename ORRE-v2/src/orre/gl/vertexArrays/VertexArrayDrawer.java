package orre.gl.vertexArrays;

import static org.lwjgl.opengl.GL11.*;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class VertexArrayDrawer {
	
	//If you get a buffer overflow error, up the byte count reserved for the buffer.
	//The class is meant to do immediate-mode ish drawing for small batches of geometry.
	
	private static final DoubleBuffer vertexBuffer = BufferUtils.createDoubleBuffer(1024);
	private static final IntBuffer indexBuffer = BufferUtils.createIntBuffer(256);
	
	public static void drawQuads(double[] vertices, int[] indices, int axisCount) {
		vertexBuffer.rewind();
		indexBuffer.rewind();
		
		vertexBuffer.put(vertices);
		indexBuffer.put(indices);
		
		vertexBuffer.rewind();
		indexBuffer.rewind();
		
		glEnableClientState(GL_VERTEX_ARRAY);         
		glVertexPointer(axisCount, 0, vertexBuffer);      
		glDrawElements(GL_QUADS, indexBuffer);
		glDisableClientState(GL_VERTEX_ARRAY);
	}
}
