package orre.geom.vbo;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL15.*;

public class GeometryBufferUpdater {
	public static void updateBufferGeometry(GeometryNode buffer, double[] updatedContents, int startIndex) {
		DoubleBuffer updatedBuffer = BufferUtils.createDoubleBuffer(updatedContents.length);
		updatedBuffer.put(updatedContents).flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, buffer.vertexBuffer);
		glBufferSubData(GL_ARRAY_BUFFER, startIndex, updatedBuffer);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
}
