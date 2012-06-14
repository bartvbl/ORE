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
	private final ArrayList<Integer> indexBuffers;
	private final ArrayList<Integer> vertexBuffers;
	private final BufferDataFormatType dataFormat;
	private int numberOfVertices;

	public GeometryBuffer(ArrayList<Integer> indexBuffers, ArrayList<Integer> vertexBuffers, BufferDataFormatType dataFormat, int numVertices)
	{
		this.numberOfVertices = numVertices;
		this.indexBuffers = indexBuffers;
		this.vertexBuffers = vertexBuffers;
		this.dataFormat = dataFormat;
		System.out.println("data format: " + dataFormat + " index/vertices: " + indexBuffers.size() + ", " + vertexBuffers.size() + ", num vertices: " + numVertices);
	}
	
	public void render()
	{
		for(int i = 0; i < this.indexBuffers.size(); i++)
		{
			this.drawBufferCombo(this.indexBuffers.get(i), this.vertexBuffers.get(i));
		}
	}
	
	private void drawBufferCombo(int indexBufferID, int vertexBufferID)
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		glBindBufferARB(GL_ARRAY_BUFFER_ARB, vertexBufferID);
		this.setDataPointers();
		glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, indexBufferID);
		glDrawRangeElements(GL_TRIANGLES, 0, this.numberOfVertices*this.dataFormat.elementSize, this.numberOfVertices, GL_UNSIGNED_INT, 0);
	}

	private void setDataPointers() {
		int stride = this.dataFormat.elementSize * 4;
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
