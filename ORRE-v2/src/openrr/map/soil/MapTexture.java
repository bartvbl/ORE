package openrr.map.soil;

import orre.gl.texture.Texture;
import orre.resources.partiallyLoadables.PartiallyLoadableTexture;

public class MapTexture {
	public final String name;
	private PartiallyLoadableTexture partiallyLoadedTexture;
	private Texture texture;

	public MapTexture(String name, PartiallyLoadableTexture texture) {
		this.name = name;
		this.partiallyLoadedTexture = texture;
	}
	
	public void finalizeTexture() {
		this.partiallyLoadedTexture.finalizeResource();
		this.texture = this.partiallyLoadedTexture.getTexture();
	}
	
	public void bind() {
		this.texture.bind();
	}
}
