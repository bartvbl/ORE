package orre.resources.loaders;

import java.util.Arrays;

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
		String x = element.getAttributeValue("x");
		String y = element.getAttributeValue("y");
		String width = element.getAttributeValue("width");
		String height = element.getAttributeValue("height");
		
		double[] xValue = readValue(x);
		double[] yValue = readValue(y);
		double[] widthValue = readValue(width);
		double[] heightValue = readValue(height);
		
		return new Bounds(
				xValue[0], xValue[1], 
				yValue[0], yValue[1], 
				widthValue[0], widthValue[1], 
				heightValue[0], heightValue[1]);
	}

	private double[] readValue(String expression) {
		double[] value = new double[2];
		StringBuffer numberBuffer = new StringBuffer();
		for(int i = 0; i < expression.length(); i++) {
			char currentChar = expression.charAt(i);
			if(Character.isDigit(currentChar) || currentChar == '.' || currentChar == '-') {
				numberBuffer.append(currentChar);
			} else if(currentChar == '%') {
				double percentValue = Double.parseDouble(numberBuffer.toString());
				value[0] = percentValue;
				//empty the string buffer for reuse
				numberBuffer.setLength(0);
			} else if(currentChar == 'p' && expression.charAt(i+1) == 'x') {
				double pixelValue = Double.parseDouble(numberBuffer.toString());
				value[1] = pixelValue;
				//empty the string buffer for reuse
				numberBuffer.setLength(0);
			}
			//ignore any other characters
		}
		return value;
	}

	@Override
	public Enum<?> getResourceType() {
		return ResourceType.menu;
	}

}
