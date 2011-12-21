package orre.gl.texture;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
	public int textureReference = -1;
	
	public Texture(int textureReference, int width, int height)
	{
		this.textureReference = textureReference;
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, this.textureReference);
	}
}
