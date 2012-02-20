package orre.gl.materials;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import orre.gl.Colour;
import orre.gl.texture.Texture;
import orre.sceneGraph.SceneNode;
import orre.sceneGraph.SimpleSceneNode;

public class Material extends SimpleSceneNode implements SceneNode, AbstractMaterial {
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
	
	public void setDiffuseTexture(Texture texture)
	{
		
	}
	
	public void setSpecularTexture(Texture texture)
	{
		
	}
	
	public void setAmbientColour(float[] colour)
	{
		colour = formatColour(colour);
		this.ambientColour.setAsRGBAArray(colour);
	}
	
	public void setDiffuseColour(float[] colour)
	{
		colour = formatColour(colour);
		this.diffuseColour.setAsRGBAArray(colour);
	}
	
	public void setSpecularColour(float[] colour)
	{
		colour = formatColour(colour);
		this.specularColour.setAsRGBAArray(colour);
	}
	private float[] formatColour(float[] colour)
	{
		if(colour.length == 3)
		{
			return new float[]{colour[0], colour[1], colour[2], 1};
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
		this.texture.bind();
		this.renderChildren();
		//glMaterial(GL_FRONT, GL_AMBIENT, );
	}
	
	public void destroy() 
	{
		
	}
}
