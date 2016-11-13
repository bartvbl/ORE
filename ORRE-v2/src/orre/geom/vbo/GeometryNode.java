package orre.geom.vbo;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import orre.rendering.RenderState;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class GeometryNode extends ContainerNode implements SceneNode {
	private final DrawingMode mode;
	private final int indexCount;
	private final int arrayID;
	private final int vertexCount;
	private final int[] bufferIDs;

	public GeometryNode(int arrayID, int indexCount, int vertexCount, DrawingMode mode, int[] bufferIDs) {
		this.arrayID = arrayID;
		this.mode = mode;
		this.indexCount = indexCount;
		this.vertexCount = vertexCount;
		this.bufferIDs = bufferIDs;
	}

	@Override
	public void render(RenderState state)
	{
		glBindVertexArray(arrayID);
		glDrawElements(mode.glEnum, indexCount, GL_UNSIGNED_INT, 0);
	}
	
	@Override
	public void destroy() {
		glDeleteVertexArrays(arrayID);
		for(int i = 0; i < bufferIDs.length; i++) {			
			glDeleteBuffers(bufferIDs[i]);
		}
	}
	
	@Override
	public String toString() {
		return "GeometryBuffer with " + vertexCount + " vertices";
	}
}
