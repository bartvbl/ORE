package orre.resources.partiallyLoadables;

import orre.gl.texture.Texture;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.resources.loaders.TextureLoader;
import orre.sceneGraph.SceneNode;

public class PartiallyLoadableTexture implements Finalizable {
	private byte[] imageData;
	private final int width;
	private final int height;
	private Texture tex;
	public final String name;
	private boolean hasBeenFinalized = false;
	private Resource resource;

	public PartiallyLoadableTexture(String name, byte[] imageData, int width, int height) {
		this.imageData = imageData;
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public Resource finalizeResource() {
		if(hasBeenFinalized) return resource;
		this.hasBeenFinalized = true;
		Texture tex = TextureLoader.createTexture(imageData, width, height);
		this.tex = tex;
		this.imageData = null;
		this.resource = new Resource(ResourceType.TEXTURE_FILE, name, Texture.class, tex);
		return resource;
	}

	public Texture getTexture()
	{
		return this.tex;
	}

}
