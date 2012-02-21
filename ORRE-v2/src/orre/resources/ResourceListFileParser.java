package orre.resources;

import java.util.List;

import org.dom4j.Node;

import orre.util.XMLDocument;

public class ResourceListFileParser {
	public static void parseFile(FileToLoad file, ResourceQueue queue)
	{
		XMLDocument resourceList = new XMLDocument(file.getPath());
		parseResourceFile(resourceList, queue, file.destinationCache);
	}
	
	private static void parseResourceFile(XMLDocument document, ResourceQueue queue, ResourceCache destinationCache)
	{
		queueNodeList(document, queue, "/resList/animations", ResourceFile.ANIMATION_FILE, destinationCache);
		queueNodeList(document, queue, "/resList/models", ResourceFile.MODEL_FILE, destinationCache);
		queueNodeList(document, queue, "/resList/sounds", ResourceFile.SOUND_FILE, destinationCache);
		queueNodeList(document, queue, "/resList/menuTextures", ResourceFile.MENU_TEXTURE_FILE, destinationCache);
	}
	
	private static void queueNodeList(XMLDocument document, ResourceQueue queue, String path, ResourceFile fileType, ResourceCache destinationCache) {
		Node mainNode = document.getSingleNode(path);
		String pathPrefix = mainNode.valueOf("@pathPrefix");
		
		List<Node> filesToLoad = document.getNodesByPath(path+"/*");
		for(Node node : filesToLoad)
		{
			FileToLoad file = new FileToLoad(node, fileType, destinationCache);
			file.pathPrefix = pathPrefix;
			queue.enqueueNodeForLoading(file);
		}
	}
}
