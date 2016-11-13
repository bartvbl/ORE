package orre.gl.texture;

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
	}
}
