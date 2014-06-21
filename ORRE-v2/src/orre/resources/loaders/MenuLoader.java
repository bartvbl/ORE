package orre.resources.loaders;

import nu.xom.Element;
import nu.xom.Elements;
import orre.gui.Bounds;
import orre.gui.Menu;
import orre.gui.controls.ImageButton;
import orre.gui.elements.Container;
import orre.gui.elements.GUIElement;
import orre.gui.elements.ImageElement;
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
		Elements childElements = rootElement.getChildElements();
		if(childElements.size() != 1) {
			throw new RuntimeException("A menu requires a single container element as its root.");
		}
		Element containerElement = rootElement.getFirstChildElement("container");
		GUIElement element = readElement(containerElement);
		return new Menu(source.name, element);
	}

	private GUIElement readElement(Element element) {
		String tagName = element.getLocalName();
		String name = element.getAttributeValue("name");
		Bounds bounds = readBounds(element);
		if(tagName.equals("container")) {
			Elements childElements = element.getChildElements();
			Container container = new Container(bounds, name);
			for(int i = 0; i < childElements.size(); i++) {
				container.addChild(readElement(childElements.get(i)));
			}
			return container;
		} else if(tagName.equals("image")) {
			String imageName = element.getAttributeValue("image");
			return new ImageElement(bounds, name, imageName);
		} else if(tagName.equals("imageButton")) {
			return ImageButton.create(bounds, 5, name);
		} else {
			throw new RuntimeException("Unknown element: " + tagName);
		}
	}

	private Bounds readBounds(Element element) {
		return null;
	}

	@Override
	public Enum<?> getResourceType() {
		return ResourceType.menu;
	}

}
