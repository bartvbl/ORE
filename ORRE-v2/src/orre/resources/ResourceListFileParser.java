package orre.resources;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

import orre.util.XMLLoader;

public class ResourceListFileParser {
	public static void parseFile(UnloadedResource file, ResourceQueue queue)
	{
		Document resourceList = XMLLoader.readXML(file.getPath());
		Element rootElement = resourceList.getRootElement();
		parseResourceFile(rootElement, queue, file.destinationCache);
	}
	
	private static void parseResourceFile(Element rootNode, ResourceQueue queue, ResourceCache destinationCache)
	{
		queueNodeList(rootNode.getFirstChildElement("animations"), queue, ResourceFile.ANIMATION_FILE, destinationCache);
		queueNodeList(rootNode.getFirstChildElement("models"), queue, ResourceFile.OBJ_MODEL_FILE, destinationCache);
		queueNodeList(rootNode.getFirstChildElement("sounds"), queue, ResourceFile.SOUND_FILE, destinationCache);
		queueNodeList(rootNode.getFirstChildElement("menuTextures"), queue, ResourceFile.MENU_TEXTURE_FILE, destinationCache);
	}
	
	private static void queueNodeList(Element fileListRoot, ResourceQueue queue, ResourceFile fileType, ResourceCache destinationCache) {
		String pathPrefix = fileListRoot.getAttributeValue("pathPrefix");
		
		Elements filesToLoad = fileListRoot.getChildElements();
		for(int i = 0; i < filesToLoad.size(); i++)
		{
			Element fileToLoadElement = filesToLoad.get(i);
			String src = fileToLoadElement.getAttributeValue("src");
			String name = fileToLoadElement.getAttributeValue("name");
			UnloadedResource file = new UnloadedResource(fileType, destinationCache, src, name);
			file.pathPrefix = pathPrefix;
			queue.enqueueNodeForLoading(file);
		}
	}
}
