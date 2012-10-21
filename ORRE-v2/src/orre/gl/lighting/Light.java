package orre.gl.lighting;

import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.glLight;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SimpleSceneNode;

public class Light extends SimpleSceneNode implements SceneNode {
	private float[] ambientLight = new float[]{0.1f, 0.1f, 0.1f, 1.0f};
	private float[] diffuseLight = new float[]{0.2f, 0.2f, 0.2f, 1.0f};
	private float[] specularLight = new float[]{0.6f, 0.6f, 0.6f, 1.0f};
	
	private FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
	
	public void render() {
		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)this.colourBuffer.put(this.ambientLight).rewind());
		glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)this.colourBuffer.put(this.diffuseLight).rewind());
		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)this.colourBuffer.put(this.specularLight).rewind());
	}
	
	public void setAmbientLight(float[] ambientLight) 
	{
		this.ambientLight = ambientLight;
	}
	
	public void setDiffuseLight(float[] diffuseLight)
	{
		this.diffuseLight = diffuseLight;
	}
	
	public void setSpecularLight(float[] specularLight)
	{
		this.specularLight = specularLight;
	}

	public void destroy() {
		
	}
}
