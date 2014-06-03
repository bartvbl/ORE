package orre.resources.loaders;

import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;

public class ScriptLoader implements ResourceTypeLoader {

	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		
		return null;
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.script;
	}

}
