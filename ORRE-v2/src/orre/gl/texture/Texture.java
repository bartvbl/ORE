package orre.gl.texture;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	private final int textureReference;
	
	public Texture(int textureReference, int width, int height)
	{
		this.textureReference = textureReference;
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
}
