package orre.resources.loaders;

import java.io.File;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.util.Logger;
import orre.util.Logger.LogType;
import orre.util.XMLLoader;

public class ResourceListFileParser implements ResourceTypeLoader {
	@Override
	public Finalizable loadResource(Resource source, ResourceQueue queue) throws Exception {
		parseFile(source, queue);
		return null;
	}
	
	@Override
	public ResourceType getResourceType() {
		return ResourceType.resourceList;
	}
	
	public static void parseFile(Resource file, ResourceQueue queue)
	{
		Document resourceList = XMLLoader.readXML(file.fileLocation);
		Element rootElement = resourceList.getRootElement();
		parseResourceFile(rootElement, queue);
	}
	
	private static void parseResourceFile(Element rootNode, ResourceQueue queue)
	{
		Elements resourceCategories = rootNode.getChildElements();
		for(int i = 0; i < resourceCategories.size(); i++) {
			Element resourceCategory = resourceCategories.get(i);
			String resourceCategoryName = resourceCategory.getLocalName();
			
			if(resourceCategoryName.equals("meta")) {
				continue;
			}
			queueNodeList(resourceCategory, queue);
		}
	}
	
	private static void queueNodeList(Element fileListRoot, ResourceQueue queue) {
		String pathPrefix = fileListRoot.getAttributeValue("pathPrefix");
		
		
		Elements filesToLoad = fileListRoot.getChildElements();
		for(int i = 0; i < filesToLoad.size(); i++)
		{
			Element fileToLoadElement = filesToLoad.get(i);
			String src = fileToLoadElement.getAttributeValue("src");
			String name = fileToLoadElement.getAttributeValue("name");
			if(name == null) {
				Logger.log("A resource with path " + src + " has no name!", LogType.ERROR);
			}
			String resourceTypeName = fileToLoadElement.getLocalName();
			try {
				Enum<?> resourceType = queue.getMatchingResourceType(resourceTypeName);
				Resource file = new Resource(new File(pathPrefix + "/" + src), resourceType, name);
				queue.enqueueNodeForLoading(file);
			} catch(IllegalArgumentException e) {
				Logger.log("Invalid resource type in resource list file: " + resourceTypeName, LogType.ERROR);
				continue;
			}
		}
	}
}
