package orre.gl;

public class Colour {
	public final float red;
	public final float green;
	public final float blue;
	public final float alpha;
	
	public Colour(float red, float green, float blue)
	{
		this(red, green, blue, 1);
	}
	
	public Colour(float red, float green, float blue, float alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
}
