package orre.geom.vbo;

import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

import java.util.ArrayList;

import orre.sceneGraph.EmptySceneNode;
import orre.sceneGraph.SceneNode;

public class GeometryBuffer extends EmptySceneNode implements SceneNode {
	private final int indexBuffer;
	private final int vertexBuffer;
	private final BufferDataFormatType dataFormat;
	private int numberOfVertices;

	public GeometryBuffer(int indexBuffer, int vertexBuffer, BufferDataFormatType dataFormat, int numVertices)
	{
		this.indexBuffer = indexBuffer;
		this.vertexBuffer = vertexBuffer;
		this.dataFormat = dataFormat;
		this.numberOfVertices = numVertices;
	}
	
	public void render()
	{
		this.drawBufferCombo(this.indexBuffer, this.vertexBuffer);
	}
	
	private void drawBufferCombo(int indexBufferID, int vertexBufferID)
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vertexBufferID);
		this.setDataPointers();
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, indexBufferID);
		glDrawElements(GL_TRIANGLES, this.numberOfVertices, GL_UNSIGNED_INT, 0);
		//glDrawRangeElements(GL_QUADS, 0, 24, 4, GL_UNSIGNED_INT, 0);
	}

	private void setDataPointers() {
		int stride = this.dataFormat.elementsPerVertex * 4;
		glVertexPointer(3, GL_FLOAT, stride, 0 * 4);
		switch(this.dataFormat)
		{
			case VERTICES_AND_TEXTURES:
				glTexCoordPointer(2, GL_FLOAT, stride, (3)*4);
			case VERTICES_AND_NORMALS:
				glNormalPointer(GL_FLOAT, stride, 3 * 4);
			case VERTICES_TEXTURES_NORMALS:
				glTexCoordPointer(2, GL_FLOAT, stride, 3 * 4);
				glNormalPointer(GL_FLOAT, stride, (3 + 3) * 4);			
		}
	}

	@Override
	public void destroy() {
		
		
	}
}
