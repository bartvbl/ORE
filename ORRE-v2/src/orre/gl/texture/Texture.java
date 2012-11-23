package orre.gl.texture;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	private final int textureReference;
	private final int width, height;
	
	public Texture(int textureReference, int width, int height)
	{
		this.textureReference = textureReference;
		this.width = width;
		this.height = height;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, this.textureReference);
	}
	
	public void blit(int x, int y, int width, int height)
	{
		this.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(x,y);
		glTexCoord2f(1,0);
		glVertex2f(x+width,y);
		glTexCoord2f(1,1);
		glVertex2f(x+width,y+height);
		glTexCoord2f(0,1);
		glVertex2f(x,y+height);
		glEnd();
	}
	
	public Texture clone() {
		return new Texture(this.textureReference, this.width, this.height);
	}
	
	public String toString() {
		return "Texture with ID " + this.textureReference;
	}
}
