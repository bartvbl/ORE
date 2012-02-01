package orre.resources;

import java.util.List;

import org.dom4j.Node;

import orre.resources.loaders.ModelLoader;
import orre.util.XMLDocument;

public class ResourceListFileParser {
	private ResourceQueue resourceQueue;
	
	public static void parseFile(String filePath, ResourceQueue queue)
	{
		XMLDocument resourceList = new XMLDocument(filePath);
		parseResourceFile(resourceList);
	}
	
	private static void parseResourceFile(XMLDocument document)
	{
		loadSounds(document);
		loadModels(document);
		loadAnimations(document);
	}

	private static void loadAnimations(XMLDocument document) {
		List<Node> animationsToLoad = document.getNodesByPath("/resList/animations/*");
		
	}

	private static void loadModels(XMLDocument document) {
		List<Node> modelsToLoad = document.getNodesByPath("/resList/models/*");
		
	}

	private static void loadSounds(XMLDocument document) {
		List<Node> soundsToLoad = document.getNodesByPath("/resList/sounds/*");
		
	}
}
