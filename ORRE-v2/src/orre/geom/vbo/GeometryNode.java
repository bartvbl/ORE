package orre.geom.vbo;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class GeometryNode extends ContainerNode implements SceneNode {
	//exposed to package to allow data to be updated
	final int indexBuffer;
	final int vertexBuffer;
	
	private final VBOFormat dataFormat;
	private final DrawingMode mode;
	private final int numberOfVertices;
	private final int indexCount;

	public GeometryNode(int indexBuffer, int vertexBuffer, VBOFormat dataFormat, int numVertices, int indexCount, DrawingMode mode)
	{
		this.indexBuffer = indexBuffer;
		this.vertexBuffer = vertexBuffer;
		this.dataFormat = dataFormat;
		this.numberOfVertices = numVertices;
		this.mode = mode;
		this.indexCount = indexCount;
	}
	
	@Override
	public void render()
	{
		this.drawBufferCombo(this.indexBuffer, this.vertexBuffer);
	}
	
	private void drawBufferCombo(int indexBufferID, int vertexBufferID)
	{
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferID);
		this.setDataPointers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferID);
		glDrawElements(mode.glEnum, indexCount, GL_UNSIGNED_INT, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
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
