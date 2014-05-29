package orre.resources;

import java.util.HashMap;

import openrr.map.Map;
import orre.animation.Animation;
import orre.animation.AnimationType;
import orre.geom.mesh.Mesh3D;
import orre.gl.texture.Texture;
import orre.resources.data.BlueprintModel;
import orre.sound.Sound;
import orre.sound.SoundType;

public class ResourceCache {
	private final HashMap<ResourceType, HashMap<String, Resource>> resourceMap;
	private final HashMap<ResourceType, Integer> nameMap;

	public ResourceCache()
	{
		resourceMap = new HashMap<ResourceType, HashMap<String, Resource>>();
		nameMap = new HashMap<ResourceType, Integer>();
	}
	
	public void addResource(Resource resource) {
		if(!resourceMap.containsKey(resource.type)) {
			resourceMap.put(resource.type, new HashMap<String, Resource>());
		}
		resourceMap.get(resource.type).put(resource.name, resource);
	}
	
	public Resource getResource(ResourceType type, String name) {
		if(resourceMap.containsKey(type)) {
			return resourceMap.get(type).get(name);
		} else {
			throw new RuntimeException("No resource of type " + type + " named " + name);
		}
	}
	
	public synchronized String uniqueName(ResourceType type) {
		if(!nameMap.containsKey(type)) {
			nameMap.put(type, 0);
		}
		int nextID = nameMap.get(type);
		String nextName = type + "_" + nextID;
		nameMap.put(type, nextID + 1);
		return nextName;
	}
}
