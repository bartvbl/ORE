package orre.gl;

import java.util.concurrent.atomic.AtomicReference;

public class Colour {
	private AtomicReference<Float> red;
	private AtomicReference<Float> green;
	private AtomicReference<Float> blue;
	private AtomicReference<Float> alpha;
	
	public Colour(float red, float green, float blue)
	{
		this.initializeAtomicReferences();
		this.setRed(red);
		this.setBlue(blue);
		this.setGreen(green);
	}
	
	public Colour(float red, float green, float blue, float alpha)
	{
		this.initializeAtomicReferences();
		this.setRed(red);
		this.setBlue(blue);
		this.setGreen(green);
		this.setAlpha(alpha);
	}
	
	public Colour()
	{
		this.initializeAtomicReferences();
	}
	
	public void setRed(float red)
	{
		this.red.set(red);
	}
	
	public void setGreen(float green)
	{
		this.green.set(green);
	}
	
	public void setBlue(float blue)
	{
		this.blue.set(blue);
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha.set(alpha);
	}
	
	public void setAsRGBAArray(float[] colour)
	{
		this.setRed(colour[0]);
		this.setGreen(colour[1]);
		this.setBlue(colour[2]);
		this.setAlpha(colour[3]);
	}
	
	private void initializeAtomicReferences()
	{
		this.red = new AtomicReference<Float>(0.2f);
		this.green = new AtomicReference<Float>(0.2f);
		this.blue = new AtomicReference<Float>(0.2f);
		this.alpha = new AtomicReference<Float>(1.0f);
	}
}
