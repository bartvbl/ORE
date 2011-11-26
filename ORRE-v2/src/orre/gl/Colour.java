package orre.gl;

public class Colour {
	private float[] ambientColour = {3f, 3f, 3f, 1f};
	private float[] diffuseColour = {3f, 3f, 3f, 1.0f};
	private float[] specularColour = {9f, 9f, 9f, 1.0f};
	
	private float alpha;
	
	public Colour()
	{
		
	}
	
	public void set()
	{
		
	}
	
	public void unset()
	{
		
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	
	public void setAmbientColour(float[] ambientColour)
	{
		this.ambientColour = ambientColour;
	}
	
	public void setDiffuseColour(float[] diffuseColour)
	{
		this.diffuseColour = diffuseColour;
	}
	
	public void setSpecularColour(float[] specularColour)
	{
		this.specularColour = specularColour;
	}
}
