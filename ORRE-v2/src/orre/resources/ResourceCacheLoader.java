package orre.resources;

import java.util.List;

import org.dom4j.Node;

import orre.resources.loaders.ModelLoader;
import orre.util.XMLDocument;

public class ResourceCacheLoader {
	private ResourceLoader resourceLoader;

	public ResourceCacheLoader(ResourceLoader loader)
	{
		this.resourceLoader = loader;
	}
	
	public void startLoading(String filePath, ResourceCache cache)
	{
		XMLDocument resourceList = new XMLDocument(filePath);
		parseResourceFile(resourceList, cache);
	}
	
	private void parseResourceFile(XMLDocument document, ResourceCache cache)
	{
		this.loadSounds(document, cache);
		this.loadModels(document, cache);
		this.loadAnimations(document, cache);
	}

	private void loadAnimations(XMLDocument document, ResourceCache cache) {
		List<Node> animationsToLoad = document.getNodesByPath("/resList/animations/*");
		new LoadingThread(this.resourceLoader, animationsToLoad, cache).start();
	}

	private void loadModels(XMLDocument document, ResourceCache cache) {
		List<Node> modelsToLoad = document.getNodesByPath("/resList/models/*");
		new LoadingThread(this.resourceLoader, modelsToLoad, cache).start();
		
	}

	private void loadSounds(XMLDocument document, ResourceCache cache) {
		List<Node> soundsToLoad = document.getNodesByPath("/resList/sounds/*");
		new LoadingThread(this.resourceLoader, soundsToLoad, cache).start();
	}
}
