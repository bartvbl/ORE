package orre.resources.partiallyLoadables;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import orre.gl.Colour;
import orre.gl.materials.AbstractMaterial;
import orre.gl.texture.Texture;
import orre.resources.Finalizable;
import orre.sceneGraph.SceneNode;

public class BlueprintMaterial extends Finalizable implements AbstractMaterial {

	public final String name;
	private Colour ambientColour;
	private Colour diffuseColour;
	private Colour specularColour;
	private boolean isColourMaterial = false;
	private float alpha;
	private Texture texture;
	
	public BlueprintMaterial(String name)
	{
		this.name = name;
		this.ambientColour = new Colour(0.2f, 0.2f, 0.2f, 1.0f);
		this.diffuseColour = new Colour(0.8f, 0.8f, 0.8f, 1.0f);
		this.specularColour = new Colour(1.0f, 1.0f, 1.0f, 1.0f);
		this.alpha = 1.0f;
	}
	
	public void setAmbientTexture(Texture texture)
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
		this.isColourMaterial = isColourMaterial;
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	
	@Override
	public void finalizeResource() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneNode createSceneNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDiffuseTexture(Texture texture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpecularTexture(Texture texture) {
		// TODO Auto-generated method stub
		
	}

}
