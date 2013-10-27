package orre.gl.lighting;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.Sphere;

import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SimpleSceneNode;

public class Light extends SimpleSceneNode implements SceneNode {
	private float[] ambientLight = new float[]{0.1f, 0.1f, 0.1f, 1.0f};
	private float[] diffuseLight = new float[]{0.2f, 0.2f, 0.2f, 1.0f};
	private float[] specularLight = new float[]{0.6f, 0.6f, 0.6f, 1.0f};
	private float[] position = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
	private float[] zero = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
	
	private FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
	
	public void render() {
		glPushMatrix();
		glTranslated(position[0], position[1], position[2] + 5);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLight(GL_LIGHT0, GL_POSITION, (FloatBuffer)this.colourBuffer.put(this.zero).rewind());
		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)this.colourBuffer.put(this.ambientLight).rewind());
		glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)this.colourBuffer.put(this.diffuseLight).rewind());
		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)this.colourBuffer.put(this.specularLight).rewind());
		glPopMatrix();
	}
	
	public void setPosition(double x, double y, double z) {
		this.position[0] = (float) x;
		this.position[1] = (float) y;
		this.position[2] = (float) z;
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
