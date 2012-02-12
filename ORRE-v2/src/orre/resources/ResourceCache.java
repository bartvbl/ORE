package orre.resources;

import orre.geom.mesh.BlueprintModel;
import orre.gl.texture.Texture;

public class ResourceCache {
	public ResourceCache()
	{
		
	}
	
	public void addTexture(Texture tex)
	{
		System.out.println("storing texture in cache: " + tex);
	}

	public void addModel(BlueprintModel model) {
		
	}
	
	//createModelInstaceByName()
	//getSoundBufferByName()
}
