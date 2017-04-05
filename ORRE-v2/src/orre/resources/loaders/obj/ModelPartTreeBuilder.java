package orre.resources.loaders.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nu.xom.Element;
import nu.xom.Elements;
import orre.resources.incompleteResources.IncompleteBlueprintModel;
import orre.resources.incompleteResources.IncompleteModelPart;

public class ModelPartTreeBuilder {
	private static final String PHYSICAL_PART = "part";
	private static final String VIRTUAL_PART = "bone";
	private static final String EMPTY_PART = "null";

	public static void populatePartTree(IncompleteBlueprintModel model, Element rootElement, HashMap<String, String> partNameLinkMap) {
		Element partStructureElement = rootElement.getFirstChildElement("partStructure");
		Elements partElements = partStructureElement.getChildElements();
		for(int i = 0; i < partElements.size(); i++)
		{
			Element currentElement = partElements.get(i);
			IncompleteModelPart modelPart = parseTreeNode(currentElement, model, partNameLinkMap);
			model.addTopLevelPart(modelPart);
		}
	}

	private static IncompleteModelPart parseTreeNode(Element nodeElement, IncompleteBlueprintModel model, HashMap<String, String> partNameLinkMap) {
		ModelPartType partType = getPartType(nodeElement);
		String partName = nodeElement.getAttributeValue("name");
		String modelFilePartName = nodeElement.getAttributeValue("nameInModelFile");
		partNameLinkMap.put(partName, modelFilePartName);
		IncompleteModelPart newPart = new IncompleteModelPart(partName, partType);
		model.registerPart(newPart);
		if(partType == ModelPartType.PHYSICAL) {			
			parsePivotLocation(newPart, nodeElement);
			parseScale(newPart, nodeElement);
		}
		if(partType == ModelPartType.BONE) {
			parsePivotLocation(newPart, nodeElement);
		}
		parseChildNodes(newPart, nodeElement, model);
		return newPart;
	}

	private static void parseScale(IncompleteModelPart newPart, Element nodeElement) {
		float scaleX = 1;
		float scaleY = 1;
		float scaleZ = 1;

		if(hasAttribute(nodeElement, "scaleX")) {
			scaleX = Float.parseFloat(nodeElement.getAttributeValue("scaleX"));
		}
		if(hasAttribute(nodeElement, "scaleY")) {
			scaleY = Float.parseFloat(nodeElement.getAttributeValue("scaleY"));
		}
		if(hasAttribute(nodeElement, "scaleZ")) {
			scaleZ = Float.parseFloat(nodeElement.getAttributeValue("scaleZ"));
		}
		
		newPart.setScale(scaleX, scaleY, scaleZ);
	}

	private static boolean hasAttribute(Element nodeElement, String attributeName) {
		return nodeElement.getAttribute(attributeName) != null;
	}
	
	private static void parsePivotLocation(IncompleteModelPart newPart, Element nodeElement) {
		String pivotXAttribute = nodeElement.getAttributeValue("pivotX");
		String pivotYAttribute = nodeElement.getAttributeValue("pivotY");
		String pivotZAttribute = nodeElement.getAttributeValue("pivotZ");

		float pivotX = Float.parseFloat(pivotXAttribute);
		float pivotY = Float.parseFloat(pivotYAttribute);
		float pivotZ = Float.parseFloat(pivotZAttribute);
		
		newPart.setPivotLocation(pivotX, pivotY, pivotZ);
	}

	private static ModelPartType getPartType(Element modelNode) {
		String partType = modelNode.getAttributeValue("type");
		partType = partType == null ? EMPTY_PART : partType;
		if(partType.equals(PHYSICAL_PART)){
			return ModelPartType.PHYSICAL;
		} else if(partType.equals(VIRTUAL_PART)){
			return ModelPartType.BONE;
		} else if(partType.equals(EMPTY_PART)){
			return ModelPartType.NULL;
		}
		return ModelPartType.PHYSICAL;
	}
	
	private static void parseChildNodes(IncompleteModelPart newPart, Element nodeElement, IncompleteBlueprintModel model) {
		Elements childElements = nodeElement.getChildElements();
		for(int i = 0; i < childElements.size(); i++) {
			newPart.addChild(parseTreeNode(childElements.get(i), model));
		}
	}
}
