package orre.resources;

import java.util.HashMap;

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
		if(resourceMap.containsKey(type) && resourceMap.get(type).containsKey(name)) {
			return resourceMap.get(type).get(name);
		}
		throw new RuntimeException("No resource of type " + type + " named " + name);
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

	public HashMap<ResourceType, HashMap<String, Resource>> debugonly_getResourceMap() {
		return resourceMap;
	}
}
