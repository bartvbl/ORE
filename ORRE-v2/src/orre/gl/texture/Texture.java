package orre.gl.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL41;

import orre.rendering.RenderState;
import orre.rendering.ShaderProperty;

public class Texture {
	public final int id;
	public final int width, height;
	
	public Texture(int textureReference, int width, int height)
	{
		this.id = textureReference;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Texture clone() {
		return new Texture(this.id, this.width, this.height);
	}
	
	@Override
	public String toString() {
		return "Texture with ID " + this.id;
	}

	public void bind(RenderState state) {
		state.shaders.setPropertyi(ShaderProperty.TEXTURE, id);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}
	
	public void unbind() {
	}
}
