package orre.resources;

import java.util.HashMap;

public class ResourceCache {
	private final HashMap<Enum<?>, HashMap<String, Resource>> resourceMap;
	private final HashMap<Enum<?>, Integer> nameMap;

	public ResourceCache()
	{
		resourceMap = new HashMap<Enum<?>, HashMap<String, Resource>>();
		nameMap = new HashMap<Enum<?>, Integer>();
	}
	
	public void addResource(Resource resource) {
		if(!resourceMap.containsKey(resource.type)) {
			resourceMap.put(resource.type, new HashMap<String, Resource>());
		}
		resourceMap.get(resource.type).put(resource.name, resource);
	}
	
	public Resource getResource(Enum<?> resourceType, String name) {
		if(resourceMap.containsKey(resourceType) && resourceMap.get(resourceType).containsKey(name)) {
			return resourceMap.get(resourceType).get(name);
		}
		throw new RuntimeException("No resource of type " + resourceType + " named " + name);
	}
	
	public synchronized String uniqueName(Enum<?> resourceType) {
		if(!nameMap.containsKey(resourceType)) {
			nameMap.put(resourceType, 0);
		}
		int nextID = nameMap.get(resourceType);
		String nextName = resourceType + "_" + nextID;
		nameMap.put(resourceType, nextID + 1);
		return nextName;
	}

	public HashMap<Enum<?>, HashMap<String, Resource>> debugonly_getResourceMap() {
		return resourceMap;
	}
}
