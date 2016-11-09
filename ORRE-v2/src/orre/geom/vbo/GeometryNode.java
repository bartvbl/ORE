package orre.geom.vbo;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import orre.gl.shaders.ActiveShader;
import orre.sceneGraph.ContainerNode;
import orre.sceneGraph.SceneNode;

public class GeometryNode extends ContainerNode implements SceneNode {
	private final DrawingMode mode;
	private final int indexCount;
	private final int arrayID;
	private final int vertexCount;

	public GeometryNode(int arrayID, int indexCount, int vertexCount, DrawingMode mode) {
		this.arrayID = arrayID;
		this.mode = mode;
		this.indexCount = indexCount;
		this.vertexCount = vertexCount;
	}

	@Override
	public void render()
	{
		glBindVertexArray(arrayID);
		glDrawElements(mode.glEnum, indexCount, GL_UNSIGNED_INT, 0);
	}
	
	@Override
	public void destroy() {
		glDeleteVertexArrays(arrayID);
	}
	
	@Override
	public String toString() {
		return "GeometryBuffer with " + vertexCount + " vertices";
	}
}
