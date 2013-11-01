package orre.resources;

import java.util.HashMap;

import openrr.map.Map;
import orre.animation.Animation;
import orre.animation.AnimationType;
import orre.geom.mesh.Mesh3D;
import orre.gl.texture.Texture;
import orre.resources.data.BlueprintModel;

public class ResourceCache {
	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private HashMap<String, BlueprintModel> models = new HashMap<String, BlueprintModel>();
	private Map map;
	private HashMap<AnimationType, Animation> animations = new HashMap<AnimationType, Animation>();

	public ResourceCache()
	{
		
	}
	
	public void addTexture(String name, Texture tex) {
		this.textures.put(name, tex);
	}
	
	public Texture getTexture(String name) {
		return textures.get(name);
	}

	public void addModel(BlueprintModel model) {
		this.models.put(model.name, model);
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public Mesh3D createModelInstace(String name) {
		return this.models.get(name).createMesh();
	}
	
	public Map getMap() {
		return this.map;
	}

	public void addAnimation(Animation animation) {
		this.animations.put(animation.type, animation);
	}

	public Animation getAnimation(AnimationType type) {
		return animations.get(type);
	}
}
