package orre.resources.loaders;

import nu.xom.Element;
import orre.resources.Finalizable;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.util.XMLLoader;

public class MenuLoader implements ResourceTypeLoader {

	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		Element rootElement = XMLLoader.readXML(source.location).getRootElement();
		if(rootElement.getChildCount() != 1) {
			throw new RuntimeException("A menu requires a single container element as its root.");
		}
		Element containerElement = rootElement.getFirstChildElement("container");
		parseContainer()
		return null;
	}

	@Override
	public Enum<?> getResourceType() {
		return ResourceType.menu;
	}

}
