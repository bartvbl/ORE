package orre.resources.partiallyLoadables;

import orre.gl.texture.Texture;
import orre.resources.Finalizable;
import orre.resources.ResourceCache;
import orre.resources.loaders.TextureLoader;

public class PartiallyLoadableTexture implements Finalizable{
	private final byte[] imageData;
	private final int width;
	private final int height;

	public PartiallyLoadableTexture(byte[] imageData, int width, int height) {
		this.imageData = imageData;
		this.width = width;
		this.height = height;
	}

	public void finalizeResource(ResourceCache cache) {
		Texture tex = TextureLoader.createTexture(imageData, width, height);
		cache.addTexture(tex);
	}

}
