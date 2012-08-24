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
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vertexBufferID);
		this.setDataPointers();
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, indexBufferID);
		glDrawElements(GL_TRIANGLES, this.numberOfVertices, GL_UNSIGNED_INT, 0);
		//glDrawRangeElements(GL_QUADS, 0, 24, 4, GL_UNSIGNED_INT, 0);
	}

	private void setDataPointers() {
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_NORMAL_ARRAY);
		
		int stride = this.dataFormat.elementsPerVertex * 4;
		glEnableClientState(GL_VERTEX_ARRAY);
		glVertexPointer(3, GL_FLOAT, stride, 0 * 4);
		switch(this.dataFormat)
		{
			case VERTICES_AND_TEXTURES:
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, GL_FLOAT, stride, (3)*4);
				break;
			case VERTICES_AND_NORMALS:
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(GL_FLOAT, stride, 3 * 4);
				break;
			case VERTICES_TEXTURES_NORMALS:
				glEnableClientState(GL_TEXTURE_COORD_ARRAY);
				glTexCoordPointer(2, GL_FLOAT, stride, 3 * 4);
				
				glEnableClientState(GL_NORMAL_ARRAY);
				glNormalPointer(GL_FLOAT, stride, (3 + 3) * 4);	
				break;
		}
	}

	@Override
	public void destroy() {
		
		
	}
}
