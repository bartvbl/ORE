package orre.gl;

import java.util.concurrent.atomic.AtomicReference;

public class Colour {
	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	public Colour(float red, float green, float blue)
	{
		this.setRed(red);
		this.setBlue(blue);
		this.setGreen(green);
	}
	
	public Colour(float red, float green, float blue, float alpha)
	{
		this.setRed(red);
		this.setBlue(blue);
		this.setGreen(green);
		this.setAlpha(alpha);
	}
	
	public Colour(){}
	
	public void setRed(float red)
	{
		this.red = red;
	}
	
	public void setGreen(float green)
	{
		this.green = green;
	}
	
	public void setBlue(float blue)
	{
		this.blue = blue;
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	
	public void setAsRGBAArray(float[] colour)
	{
		this.setRed(colour[0]);
		this.setGreen(colour[1]);
		this.setBlue(colour[2]);
		this.setAlpha(colour[3]);
	}
}
