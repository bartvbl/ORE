package orre.geom.vbo;

import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class GeometryNode extends ContainerNode implements SceneNode {
	private final int indexBuffer;
	private final int vertexBuffer;
	private final BufferDataFormatType dataFormat;
	private final DrawingMode mode;
	private final int numberOfVertices;

	public GeometryNode(int indexBuffer, int vertexBuffer, BufferDataFormatType dataFormat, int numVertices, DrawingMode mode)
	{
		this.indexBuffer = indexBuffer;
		this.vertexBuffer = vertexBuffer;
		this.dataFormat = dataFormat;
		this.numberOfVertices = numVertices;
		this.mode = mode;
	}
	
	@Override
	public void render()
	{
		this.drawBufferCombo(this.indexBuffer, this.vertexBuffer);
	}
	
	private void drawBufferCombo(int indexBufferID, int vertexBufferID)
	{
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vertexBufferID);
		this.setDataPointers();
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, indexBufferID);
		glDrawElements(mode.glEnum, this.numberOfVertices, GL_UNSIGNED_INT, 0);
		this.unsetDataPointers();
	}

	private void setDataPointers() {
		//format: 0    1    2    3   4   5    6    7
		//format: vert vert vert tex tex norm norm norm	
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
		int bytesPerDouble = 8;
		int stride = this.dataFormat.elementsPerVertex * bytesPerDouble;
		glEnableClientState(GL_VERTEX_ARRAY);
		glVertexPointer(3, GL_DOUBLE, stride, 0 * bytesPerDouble);
		switch(this.dataFormat)
		{
			case VERTICES_AND_TEXTURES:
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, GL_DOUBLE, stride, 3 * bytesPerDouble);
				break;
			case VERTICES_AND_NORMALS:
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(GL_DOUBLE, stride, 3 * bytesPerDouble);
				break;
			case VERTICES_TEXTURES_NORMALS:
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, GL_DOUBLE, stride, 3 * bytesPerDouble);
				
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(GL_DOUBLE, stride, (3 + 2) * bytesPerDouble);
				break;
		}
	}
	
	private void unsetDataPointers() {
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
	}

	@Override
	public void destroy() {
		IntBuffer bufferIDBuffer = BufferUtils.createIntBuffer(2);
		bufferIDBuffer.put(indexBuffer);
		bufferIDBuffer.put(vertexBuffer);
		bufferIDBuffer.rewind();
		glDeleteBuffers(bufferIDBuffer);
	}
	
	@Override
	public String toString() {
		return "GeometryBuffer with " + numberOfVertices + " vertices";
	}
}
