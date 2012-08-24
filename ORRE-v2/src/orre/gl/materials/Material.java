package orre.gl.materials;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.lwjgl.BufferUtils;

import orre.gl.Colour;
import orre.gl.texture.Texture;
import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SimpleSceneNode;

import static org.lwjgl.opengl.GL11.*;

public class Material extends SimpleSceneNode implements SceneNode, AbstractMaterial {
	public final String name;
	private float[] ambientColour;
	private float[] diffuseColour;
	private float[] specularColour;
	private float[] emissionColour;
	private AtomicBoolean isColourMaterial = new AtomicBoolean(false);
	private AtomicReference<Float> alpha;
	private Texture ambientTexture = null;
	private Texture diffuseTexture = null;
	private Texture specularTexture = null;
	private FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
	private static final float[] ambientLight = new float[]{0.1f, 0.1f, 0.1f, 1.0f};
	private static final float[] diffuseLight = new float[]{0.2f, 0.2f, 0.2f, 1.0f};
	private static final float[] specularLight = new float[]{0.6f, 0.6f, 0.6f, 1.0f};
	private static final float[] emissionLight = new float[]{0.2f, 0.2f, 0.2f, 1.0f};
	
	public Material(String name)
	{
		this.name = name;
		this.ambientColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.diffuseColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.specularColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.emissionColour = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
		this.alpha = new AtomicReference<Float>();
		this.alpha.set(1f);
	}
	
	public void setAmbientTexture(Texture texture)
	{
		this.ambientTexture = texture;
	}
	public void setDiffuseTexture(Texture texture)
	{
		this.diffuseTexture = texture;
	}
	public void setSpecularTexture(Texture texture)
	{
		this.specularTexture = texture;
	}
	
	public void setAmbientColour(float[] colour)
	{
		colour = formatColour(colour);
		this.ambientColour = colour;
	}
	public void setDiffuseColour(float[] colour)
	{
		colour = formatColour(colour);
		this.diffuseColour = colour;
	}
	public void setSpecularColour(float[] colour)
	{
		colour = formatColour(colour);
		this.specularColour = colour;
	}
	public void setEmissionColour(float[] colour)
	{
		colour = formatColour(colour);
		this.emissionColour = colour;
	}
	
	private float[] formatColour(float[] colour)
	{
		if(colour.length == 3)
		{
			return new float[]{colour[0], colour[1], colour[2], 1f};
		}
		return colour;
	}
	
	public void setMaterialAsColourMaterial(boolean isColourMaterial)
	{
		this.isColourMaterial.set(isColourMaterial);
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha.set(alpha);
	}
	
	public void render() 
	{
		if(this.isColourMaterial.get()) {
			glEnable(GL_COLOR_MATERIAL);				
			glColorMaterial(GL_FRONT, GL_AMBIENT);							
			glColorMaterial(GL_FRONT, GL_DIFFUSE);							
			glColorMaterial(GL_FRONT, GL_SPECULAR);							
			glColor4f(this.ambientColour[0], this.ambientColour[1], this.ambientColour[2], this.alpha.get());
		}
		if(this.diffuseTexture != null) {
			glEnable(GL_TEXTURE_2D);
			this.diffuseTexture.bind();
		} else {
			glDisable(GL_TEXTURE_2D);
		}
		glMaterialf(GL_FRONT, GL_SHININESS, 10f);
		glLight(GL_LIGHT0, GL_AMBIENT, (FloatBuffer)this.colourBuffer.put(this.ambientLight).rewind());
		glLight(GL_LIGHT0, GL_DIFFUSE, (FloatBuffer)this.colourBuffer.put(this.diffuseLight).rewind());
		glLight(GL_LIGHT0, GL_SPECULAR, (FloatBuffer)this.colourBuffer.put(this.specularLight).rewind());
//		glLight(GL_LIGHT0, GL_EMISSION, (FloatBuffer)this.colourBuffer.put(this.emissionLight).rewind());
		glMaterial(GL_FRONT, GL_AMBIENT, (FloatBuffer)this.colourBuffer.put(this.ambientColour).rewind());
		glMaterial(GL_FRONT, GL_DIFFUSE, (FloatBuffer)this.colourBuffer.put(this.diffuseColour).rewind());
		glMaterial(GL_FRONT, GL_SPECULAR, (FloatBuffer)this.colourBuffer.put(this.specularColour).rewind());
		glMaterial(GL_FRONT, GL_EMISSION, (FloatBuffer)this.colourBuffer.put(this.emissionColour).rewind());
		this.renderChildren();
		glDisable(GL_COLOR_MATERIAL);
	}
	
	public void destroy() 
	{
		
	}
	
	public Material clone() {
		Material material = new Material(this.name);
		material.setAlpha(this.alpha.get());
		material.setAmbientColour(this.ambientColour);
		material.setDiffuseColour(this.diffuseColour);
		material.setSpecularColour(this.specularColour);
		material.setEmissionColour(this.emissionColour);
		if(this.ambientTexture != null) {material.setAmbientTexture(this.ambientTexture.clone());}
		if(this.diffuseTexture != null) {material.setDiffuseTexture(this.diffuseTexture.clone());}
		if(this.specularTexture != null) {material.setSpecularTexture(this.specularTexture.clone());}
		material.setMaterialAsColourMaterial(this.isColourMaterial.get());
		return material;
	}
}
