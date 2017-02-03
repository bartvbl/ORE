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
	
	public ResourceObject<?> getResource(Enum<?> resourceType, String name) {
		if(resourceMap.containsKey(resourceType)) {
			if(resourceMap.get(resourceType).containsKey(name)) {
				Resource resource = resourceMap.get(resourceType).get(name);
				ResourceState currentState = resource.currentState();
				if(currentState != ResourceState.LOADED) {
					if(currentState == ResourceState.UNLOADED) {
						
					}
				}
				return resource.content;					
			} else {
				throw new RuntimeException("No resource of type " + resourceType + " named \"" + resourceType + "\" could be found. Make sure you added it to a resource list.");
			}
		} else {
			throw new RuntimeException("Unknown resource type requested: \"" + resourceType + "\".");
		}
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

	public void registerResources(ResourceList resourceList) {
		for(Resource resource : resourceList.resources) {
			if(!resourceMap.containsKey(resource.type)) {
				resourceMap.put(resource.type, new HashMap<String, Resource>());
			}
			resourceMap.get(resource.type).put(resource.name, resource);
		}
	}

	
}
