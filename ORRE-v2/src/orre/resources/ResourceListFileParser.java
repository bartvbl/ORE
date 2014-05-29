package orre.resources;

import java.io.File;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import orre.util.XMLLoader;

public class ResourceListFileParser implements ResourceTypeLoader {
	@Override
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception {
		parseFile(source, queue);
		return null;
	}
	
	@Override
	public ResourceType getResourceType() {
		return ResourceType.RESOURCE_LIST_FILE;
	}
	
	public static void parseFile(UnloadedResource file, ResourceQueue queue)
	{
		Document resourceList = XMLLoader.readXML(file.location);
		Element rootElement = resourceList.getRootElement();
		parseResourceFile(rootElement, queue);
	}
	
	private static void parseResourceFile(Element rootNode, ResourceQueue queue)
	{
		queueNodeList(rootNode.getFirstChildElement("animations"), queue, ResourceType.ANIMATION_FILE);
		queueNodeList(rootNode.getFirstChildElement("models"), queue, ResourceType.OBJ_MODEL_FILE);
		queueNodeList(rootNode.getFirstChildElement("sounds"), queue, ResourceType.SOUND_FILE);
		queueNodeList(rootNode.getFirstChildElement("textures"), queue, ResourceType.TEXTURE_FILE);
	}
	
	private static void queueNodeList(Element fileListRoot, ResourceQueue queue, ResourceType fileType) {
		String pathPrefix = fileListRoot.getAttributeValue("pathPrefix");
		
		Elements filesToLoad = fileListRoot.getChildElements();
		for(int i = 0; i < filesToLoad.size(); i++)
		{
			Element fileToLoadElement = filesToLoad.get(i);
			String src = fileToLoadElement.getAttributeValue("src");
			String name = fileToLoadElement.getAttributeValue("name");
			UnloadedResource file = new UnloadedResource(fileType, new File(pathPrefix + "/" + src), name);
			queue.enqueueNodeForLoading(file);
		}
	}
}
