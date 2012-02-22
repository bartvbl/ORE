package orre.resources;

import java.util.HashMap;

import orre.geom.mesh.Mesh3D;
import orre.gl.texture.Texture;
import orre.resources.data.BlueprintModel;

public class ResourceCache {
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private HashMap<String, BlueprintModel> models = new HashMap<String, BlueprintModel>();

	public ResourceCache()
	{
		
	}
	
	public void addTexture(String name, Texture tex) {
		this.textures.put(name, tex);
	}

	public void addModel(BlueprintModel model) {
		System.out.println("adding model: " + model.name);
		this.models.put(model.name, model);
	}
	
	public Mesh3D createModelInstace(String name) {
		return this.models.get(name).createSceneNode();
	}
	
	//createModelInstaceByName()
	//getSoundBufferByName()
}
