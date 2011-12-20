package orre.gl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SimpleSceneNode;
import static org.lwjgl.opengl.GL11.*;

public class Material extends SimpleSceneNode implements SceneNode {
	public final String name;
	private Colour ambientColour;
	private Colour diffuseColour;
	private Colour specularColour;
	private AtomicBoolean isColourMaterial = new AtomicBoolean(false);
	private AtomicReference<Float> alpha;
	private Texture texture;
	
	public Material(String name)
	{
		this.name = name;
		this.ambientColour = new Colour(0.2f, 0.2f, 0.2f, 1.0f);
		this.diffuseColour = new Colour(0.8f, 0.8f, 0.8f, 1.0f);
		this.specularColour = new Colour(1.0f, 1.0f, 1.0f, 1.0f);
		this.alpha = new AtomicReference<Float>();
	}
	
	public void setTexture(Texture texture)
	{
		
	}
	
	public void setAmbientTexture(Texture texture)
	{
		
	}
	
	public void setAmbientColour(float[] colour)
	{
		this.ambientColour.setAsRGBAArray(colour);
	}
	
	public void setDiffuseColour(float[] colour)
	{
		this.diffuseColour.setAsRGBAArray(colour);
	}
	
	public void setSpecularColour(float[] colour)
	{
		this.specularColour.setAsRGBAArray(colour);
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
		glBindTexture(GL_TEXTURE_2D, this.texture.texRef);
		this.renderChildren();
		//glMaterial(GL_FRONT, GL_AMBIENT, );
	}
	
	public void destroy() 
	{
		
	}
}
