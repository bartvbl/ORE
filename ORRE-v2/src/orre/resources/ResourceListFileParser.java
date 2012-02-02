package orre.resources;

import java.util.List;

import org.dom4j.Node;

import orre.util.XMLDocument;

public class ResourceListFileParser {
	public static void parseFile(String filePath, ResourceQueue queue)
	{
		XMLDocument resourceList = new XMLDocument(filePath);
		parseResourceFile(resourceList, queue);
	}
	
	private static void parseResourceFile(XMLDocument document, ResourceQueue queue)
	{
		queueNodeList(document, queue, "/resList/animations", ResourceFile.ANIMATION_FILE);
		queueNodeList(document, queue, "/resList/models", ResourceFile.MODEL_FILE);
		queueNodeList(document, queue, "/resList/sounds", ResourceFile.SOUND_FILE);
		queueNodeList(document, queue, "/resList/menuTextures", ResourceFile.MENU_TEXTURE_FILE);
	}
	
	private static void queueNodeList(XMLDocument document, ResourceQueue queue, String path, ResourceFile fileType) {
		Node mainNode = document.getSingleNode(path);
		String pathPrefix = mainNode.valueOf("@pathPrefix");
		
		List<Node> filesToLoad = document.getNodesByPath(path+"/*");
		for(Node node : filesToLoad)
		{
			FileToLoad file = new FileToLoad(node, fileType);
			file.pathPrefix = pathPrefix;
			queue.enqueueNodeForLoading(file);
		}
	}
}
