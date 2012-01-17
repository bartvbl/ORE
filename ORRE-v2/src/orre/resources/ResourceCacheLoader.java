package orre.resources;

import java.util.List;

import org.dom4j.Node;

import orre.resources.loaders.ModelLoader;
import orre.util.XMLDocument;

public class ResourceCacheLoader {
	public static ResourceCache load(String filePath)
	{
		ResourceCache cache = new ResourceCache();
		XMLDocument resourceList = new XMLDocument(filePath);
		parseResourceFile(resourceList, cache);
		return cache;
	}
	
	private static void parseResourceFile(XMLDocument document, ResourceCache cache)
	{
		loadSounds(document, cache);
		loadModels(document, cache);
		loadAnimations(document, cache);
	}

	private static void loadAnimations(XMLDocument document, ResourceCache cache) {
		List<Node> animationsToLoad = document.getNodesByPath("/resList/animations/*");
	}

	private static void loadModels(XMLDocument document, ResourceCache cache) {
		List<Node> modelsToLoad = document.getNodesByPath("/resList/models/*");
		for(Node modelToLoad : modelsToLoad)
		{
			TemplateMesh model = ModelLoader.loadModel(modelToLoad.valueOf("@src"));
		}
	}

	private static void loadSounds(XMLDocument document, ResourceCache cache) {
		List<Node> soundsToLoad = document.getNodesByPath("/resList/sounds/*");
	}
}
